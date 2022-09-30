import { Component, Emit, Prop, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { AkipRelationship } from '@/shared/model/akip-relationship.model';
import { AkipEntity } from '@/shared/model/akip-entity.model';
import { required } from 'vuelidate/lib/validators';

const validations: any = {
  akipRelationship: {
    relationshipName: {
      required,
    },
    relationshipType: {
      required,
    },
    otherEntityName: {
      required,
    },
    otherEntityField: {},
    relationshipValidateRules: {},
    otherEntityRelationshipName: {},
  },
};

@Component({
  validations,
})
export default class ShowAkipRelationshipComponent extends mixins(JhiDataUtils) {
  @Prop()
  akipRelationshipProp: AkipRelationship;

  @Prop({ default: false })
  readOnly: boolean;

  @Prop()
  otherAkipEntities: Array<AkipEntity>;

  @Prop()
  index: number;

  @Prop()
  removeFunction;

  private akipRelationship: AkipRelationship = new AkipRelationship();

  @Watch('$v.akipRelationship.$invalid')
  onAkipRelationshipInvalidChanged() {
    console.log('AQUI');
    this.$emit('is-akip-relationship-invalid', this.$v.akipRelationship.$invalid);
  }

  @Watch('akipRelationshipProp')
  ondAkipReationshipPropValueChange() {
    this.updateAkipRelationship();
  }

  mounted() {
    this.updateAkipRelationship();
  }

  public updateAkipRelationship() {
    if (this.akipRelationshipProp) {
      this.akipRelationship = this.akipRelationshipProp;
    }
  }

  get getOtherAkipEntityFields() {
    if (this.otherAkipEntities && this.akipRelationship.otherEntityName) {
      return this.otherAkipEntities.find(otherAkipEntity => otherAkipEntity.name == this.akipRelationship.otherEntityName).fields;
    }
    return null;
  }

  public changeValueInRelationshipValidateRules(value: string) {
    if (
      this.akipRelationship.relationshipValidateRules &&
      this.akipRelationship.relationshipValidateRules.find(validate => validate == value)
    ) {
      this.akipRelationship.relationshipValidateRules = this.akipRelationship.relationshipValidateRules.filter(
        validate => validate != value
      );
      return;
    }
    if (!this.akipRelationship.relationshipValidateRules) {
      this.akipRelationship.relationshipValidateRules = [];
    }
    this.akipRelationship.relationshipValidateRules.push(value);
  }
}
