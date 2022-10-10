import { Component, Emit, Prop, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { AkipRelationship } from '@/shared/model/akip-relationship.model';
import { AkipEntity } from '@/shared/model/akip-entity.model';
import { required } from 'vuelidate/lib/validators';
import { TypeEntity } from '../shared/model/enumerations/type-entity.model';

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
  otherAkipEntitiesProp: Array<AkipEntity>;

  @Prop()
  index: number;

  @Prop()
  removeFunction;

  @Prop()
  typeEntity: TypeEntity;

  private akipRelationship: AkipRelationship = new AkipRelationship();
  private otherAkipEntities: Array<AkipEntity> = new Array<AkipEntity>();

  @Watch('$v.akipRelationship.$invalid')
  onAkipRelationshipInvalidChanged() {
    this.$emit('is-akip-relationship-invalid', this.$v.akipRelationship.$invalid);
  }

  @Watch('akipRelationshipProp')
  ondAkipReationshipPropValueChange() {
    this.updateAkipRelationship();
  }

  @Watch('otherAkipEntitiesProp')
  onOtherAkipEntitiesPropValueChange() {
    this.updateOtherAkipEntities();
  }

  @Watch('akipRelationship.relationshipType')
  onAkipRelationshipTypeValueChange() {
    if (this.akipRelationship.relationshipType != 'one-to-many' && this.akipRelationship.relationshipType != 'one-to-one') {
      this.akipRelationship.otherEntityRelationshipName = null;
    }
  }

  mounted() {
    this.updateAkipRelationship();
    this.updateOtherAkipEntities();
  }

  public updateAkipRelationship() {
    if (this.akipRelationshipProp) {
      this.akipRelationship = this.akipRelationshipProp;
    }
  }

  public updateOtherAkipEntities() {
    if (this.otherAkipEntitiesProp) {
      this.otherAkipEntities = this.otherAkipEntitiesProp;
    }
  }

  get getOtherAkipEntityFields() {
    if (this.otherAkipEntities && this.akipRelationship.otherEntityName) {
      let otherAkipEntity: AkipEntity = this.otherAkipEntities.find(
        otherAkipEntity => otherAkipEntity.name == this.akipRelationship.otherEntityName
      );
      if (!otherAkipEntity.fields) {
        return null;
      }
      return otherAkipEntity.fields;
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
