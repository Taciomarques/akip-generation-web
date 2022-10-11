import { Component, Prop, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, requiredIf } from 'vuelidate/lib/validators';
import { AkipField } from '@/shared/model/akip-field.model';
import { TypeEntity } from '@/shared/model/enumerations/type-entity.model';

const validations: any = {
  akipField: {
    fieldName: {
      required,
    },
    fieldType: {
      required,
    },
    fieldReadOnly: {
      required: requiredIf(function () {
        return (this.typeEntity && this.typeEntity == 'USER_TASK') || (this.typeEntity && this.typeEntity == 'START_FORM');
      }),
    },
    fieldValues: {
      required: requiredIf(function () {
        return this.fieldTypeTemp == 'enum';
      }),
    },
    fieldTypeBlobContent: {
      required: requiredIf(function () {
        return this.akipField.fieldType == 'byte[]';
      }),
    },
    fieldValidateRulesMin: {
      required: requiredIf(function () {
        return (
          (this.akipField.fieldType == 'Integer' ||
            this.akipField.fieldType == 'Long' ||
            this.akipField.fieldType == 'Double' ||
            this.akipField.fieldType == 'BigDecimal' ||
            this.akipField.fieldType == 'Float') &&
          this.akipField.fieldValidateRules &&
          this.akipField.fieldValidateRules.includes('min')
        );
      }),
    },
    fieldValidateRulesMax: {
      required: requiredIf(function () {
        return (
          (this.akipField.fieldType == 'Integer' ||
            this.akipField.fieldType == 'Long' ||
            this.akipField.fieldType == 'Double' ||
            this.akipField.fieldType == 'BigDecimal' ||
            this.akipField.fieldType == 'Float') &&
          this.akipField.fieldValidateRules &&
          this.akipField.fieldValidateRules.includes('max')
        );
      }),
    },
    fieldValidateRulesMinlength: {
      required: requiredIf(function () {
        return (
          (this.akipField.fieldType == 'String' ||
            (this.akipField.fieldType == 'byte[]' && this.akipField.fieldTypeBlobContent == 'text')) &&
          this.akipField.fieldValidateRules &&
          this.akipField.fieldValidateRules.includes('minlength')
        );
      }),
    },
    fieldValidateRulesMaxlength: {
      required: requiredIf(function () {
        return (
          (this.akipField.fieldType == 'String' ||
            (this.akipField.fieldType == 'byte[]' && this.akipField.fieldTypeBlobContent == 'text')) &&
          this.akipField.fieldValidateRules &&
          this.akipField.fieldValidateRules.includes('maxlength')
        );
      }),
    },
    fieldValidateRulesPattern: {
      required: requiredIf(function () {
        return (
          (this.akipField.fieldType == 'String' ||
            (this.akipField.fieldType == 'byte[]' && this.akipField.fieldTypeBlobContent == 'text')) &&
          this.akipField.fieldValidateRules &&
          this.akipField.fieldValidateRules.includes('pattern')
        );
      }),
    },
    fieldValidateRules: {},
  },
  fieldTypeTemp: {
    required,
  },
};

@Component({
  validations,
})
export default class ShowAkipFieldComponent extends mixins(JhiDataUtils) {
  @Prop()
  akipFieldProp: AkipField;

  @Prop({ default: false })
  readOnly: boolean;

  @Prop({ default: false })
  akipEntityReadOnly: boolean;

  @Prop()
  index: number;

  @Prop()
  typeEntity: TypeEntity;

  @Prop()
  removeFunction;

  private akipField: AkipField = new AkipField();

  private fieldTypeTemp: string = null;

  private backupFieldValidade: any = {};

  @Watch('$v.akipField.$invalid', { immediate: true })
  onAkipFieldInvalidChanged() {
    this.$emit('is-akip-field-invalid', this.$v.akipField.$invalid);
  }

  @Watch('akipEntityReadOnly')
  ondAkipEntityReadOnlyValueChange() {
    this.resetValuesInFieldValidateRules();
  }
  @Watch('akipFieldProp')
  ondAkipFieldPropValueChange() {
    this.updateAkipField();
  }

  @Watch('akipField.fieldName')
  onFieldNameValueChange() {
    if (this.akipField.fieldName) {
      this.akipField.fieldName = this.akipField.fieldName.trim();
    }
  }

  @Watch('akipField.fieldValues')
  onFieldValuesValueChange() {
    if (this.akipField.fieldValues) {
      this.akipField.fieldValues = this.akipField.fieldValues.trim().toUpperCase();
    }
  }

  @Watch('akipField.fieldType')
  onFieldTypeValueChange() {
    if (this.akipField.fieldType) {
      this.akipField.fieldType = this.akipField.fieldType.trim();
    }
  }

  @Watch('fieldTypeTemp')
  onFieldTypeTemValueChange() {
    if (this.fieldTypeTemp == 'enum') {
      this.akipField.fieldType = '';
      return;
    }
    if (this.akipField.fieldValues) {
      this.akipField.fieldValues = null;
    }
    if (this.akipField.fieldTypeBlobContent) {
      this.akipField.fieldTypeBlobContent = null;
    }
    if (this.fieldTypeTemp) {
      this.akipField.fieldType = this.fieldTypeTemp;
    }
  }

  mounted() {
    this.updateAkipField();
  }

  public updateAkipField() {
    if (this.akipFieldProp) {
      this.akipField = this.akipFieldProp;
      this.fieldTypeTemp = this.akipField.fieldType;
    }
  }

  public changeValueInFieldValidateRules(value: string) {
    if (this.akipField.fieldValidateRules && this.akipField.fieldValidateRules.find(validate => validate == value)) {
      this.akipField.fieldValidateRules = this.akipField.fieldValidateRules.filter(validate => validate != value);
      return;
    }
    if (!this.akipField.fieldValidateRules) {
      this.akipField.fieldValidateRules = [];
    }
    this.akipField.fieldValidateRules.push(value);
  }

  public resetValuesInFieldValidateRules() {
    if (this.akipField.fieldValidateRulesMin && this.akipField.fieldValidateRules.includes('min')) {
      this.akipField.fieldValidateRulesMin = null;
    }

    if (this.akipField.fieldValidateRulesMax && this.akipField.fieldValidateRules.includes('max')) {
      this.akipField.fieldValidateRulesMax = null;
    }

    if (this.akipField.fieldValidateRulesMinlength && this.akipField.fieldValidateRules.includes('minlength')) {
      this.akipField.fieldValidateRulesMinlength = null;
    }

    if (this.akipField.fieldValidateRulesMaxlength && this.akipField.fieldValidateRules.includes('maxlength')) {
      this.akipField.fieldValidateRulesMaxlength = null;
    }

    if (this.akipField.fieldValidateRulesPattern && this.akipField.fieldValidateRules.includes('pattern')) {
      this.akipField.fieldValidateRulesPattern = null;
    }

    this.akipField.fieldValidateRules = [];
  }

  public backupValuesInFieldValidateRules() {
    if (this.akipField.fieldReadOnly) {
      if (this.akipField.fieldValidateRulesMin && this.akipField.fieldValidateRules.includes('min')) {
        this.backupFieldValidade.fieldValidateRulesMin = this.akipField.fieldValidateRulesMin;
        this.akipField.fieldValidateRulesMin = null;
      }

      if (this.akipField.fieldValidateRulesMax && this.akipField.fieldValidateRules.includes('max')) {
        this.backupFieldValidade.fieldValidateRulesMax = this.akipField.fieldValidateRulesMax;
        this.akipField.fieldValidateRulesMax = null;
      }

      if (this.akipField.fieldValidateRulesMinlength && this.akipField.fieldValidateRules.includes('minlength')) {
        this.backupFieldValidade.fieldValidateRulesMinlength = this.akipField.fieldValidateRulesMinlength;
        this.akipField.fieldValidateRulesMinlength = null;
      }

      if (this.akipField.fieldValidateRulesMaxlength && this.akipField.fieldValidateRules.includes('maxlength')) {
        this.backupFieldValidade.fieldValidateRulesMaxlength = this.akipField.fieldValidateRulesMaxlength;
        this.akipField.fieldValidateRulesMaxlength = null;
      }

      if (this.akipField.fieldValidateRulesPattern && this.akipField.fieldValidateRules.includes('pattern')) {
        this.backupFieldValidade.fieldValidateRulesPattern = this.akipField.fieldValidateRulesPattern;
        this.akipField.fieldValidateRulesPattern = null;
      }
      this.backupFieldValidade.fieldValidateRules = this.akipField.fieldValidateRules;
      this.akipField.fieldValidateRules = [];
      return;
    }

    if (this.backupFieldValidade.fieldValidateRulesMin && this.backupFieldValidade.fieldValidateRules.includes('min')) {
      this.akipField.fieldValidateRulesMin = this.backupFieldValidade.fieldValidateRulesMin;
    }

    if (this.backupFieldValidade.fieldValidateRulesMax && this.backupFieldValidade.fieldValidateRules.includes('max')) {
      this.akipField.fieldValidateRulesMax = this.backupFieldValidade.fieldValidateRulesMax;
    }

    if (this.backupFieldValidade.fieldValidateRulesMinlength && this.backupFieldValidade.fieldValidateRules.includes('minlength')) {
      this.akipField.fieldValidateRulesMinlength = this.backupFieldValidade.fieldValidateRulesMinlength;
    }

    if (this.backupFieldValidade.fieldValidateRulesMaxlength && this.backupFieldValidade.fieldValidateRules.includes('maxlength')) {
      this.akipField.fieldValidateRulesMaxlength = this.backupFieldValidade.fieldValidateRulesMaxlength;
    }

    if (this.backupFieldValidade.fieldValidateRulesPattern && this.backupFieldValidade.fieldValidateRules.includes('pattern')) {
      this.akipField.fieldValidateRulesPattern = this.backupFieldValidade.fieldValidateRulesPattern;
    }
    this.akipField.fieldValidateRules = this.backupFieldValidade.fieldValidateRules;
  }
}
