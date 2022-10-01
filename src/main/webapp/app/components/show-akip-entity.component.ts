import { Component, Inject, Prop, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';
import { AkipField } from '@/shared/model/akip-field.model';
import { AkipRelationship } from '@/shared/model/akip-relationship.model';
import { AkipEntity } from '@/shared/model/akip-entity.model';
import AkipEntityService from '@/entities/akip-entity/akip-entity.service';
import { TypeEntity } from '../shared/model/enumerations/type-entity.model';

const validations: any = {
  akipEntity: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ShowAkipEntityComponent extends mixins(JhiDataUtils) {
  @Inject('akipEntityService')
  private akipEntityService: () => AkipEntityService;

  @Prop()
  akipEntityProp: AkipEntity;

  @Prop()
  applicationId: number;

  @Prop()
  typeEntity: TypeEntity;

  @Prop({ default: false })
  readOnly: boolean;

  private akipEntity: AkipEntity = new AkipEntity();

  private otherAkipEntities: Array<AkipEntity> = new Array<AkipEntity>();

  public fieldsInvalid: boolean[] = [];

  public relationshipsInvalid: boolean[] = [];

  public collapseController: any = {
    showAkipEntity: true,
  };

  @Watch('$v.akipEntity.$invalid')
  @Watch('fieldsInvalid')
  @Watch('relationshipsInvalid')
  onAkipEntityInvalidChanged() {
    this.$emit('is-akip-entity-invalid', this.isAkipEntityInvalid);
  }

  @Watch('akipEntityProp')
  onAkipEntityPropValueChange() {
    this.updateAkipEntity();
  }

  @Watch('applicationId')
  onApplicationIdValueChange() {
    this.findAkipEntitiesById();
  }

  @Watch('akipEntity.name')
  @Watch('akipEntity.fields')
  @Watch('akipEntity.relationships')
  ondAkipEntityValueChange() {
    this.$emit('update-akip-entity', this.akipEntity);
  }

  mounted() {
    this.updateAkipEntity();
    this.findAkipEntitiesById();
  }

  public updateAkipEntity() {
    if (this.akipEntityProp) {
      this.akipEntity = this.akipEntityProp;
      if (!this.akipEntityProp.fields) {
        this.akipEntity.fields = [];
      }
      if (!this.akipEntityProp.relationships) {
        this.akipEntity.relationships = [];
      }
    }
  }

  public findAkipEntitiesById() {
    if (this.applicationId) {
      this.findAkipEntitiesApplicationById(this.applicationId);
    }
  }

  public collapse(collapseComponent: string): void {
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
  }

  get isAkipEntityInvalid() {
    if (
      this.$v.akipEntity.$invalid ||
      ((!this.akipEntity.fields || !this.akipEntity.fields.length) && this.typeEntity == 'DOMAIN') ||
      this.isAnyFieldInvalid ||
      this.isAnyRelationshipInvalid
    ) {
      return true;
    }
    return false;
  }

  get isAnyFieldInvalid() {
    return this.fieldsInvalid.find(elem => elem);
  }

  get isAnyRelationshipInvalid() {
    return this.relationshipsInvalid.find(elem => elem);
  }

  public updateFieldsInvalid(indexField, isFieldInvalid) {
    this.fieldsInvalid.splice(indexField, 1, isFieldInvalid);
  }

  public addField() {
    this.akipEntity.fields.push(new AkipField());
    this.fieldsInvalid.push(true);
  }

  public removeField(index) {
    this.akipEntity.fields.splice(index, 1);
    this.fieldsInvalid.splice(index, 1);
  }

  public updateRelationshipsInvalid(indexRelationship, isRelationshipInvalid) {
    this.relationshipsInvalid.splice(indexRelationship, 1, isRelationshipInvalid);
  }

  public addRelationship() {
    this.akipEntity.relationships.push(new AkipRelationship());
    this.relationshipsInvalid.push(true);
  }

  public removeRelationship(index) {
    this.akipEntity.relationships.splice(index, 1);
    this.relationshipsInvalid.splice(index, 1);
  }

  public findAkipEntitiesApplicationById(applicationId: number) {
    this.akipEntityService()
      .findByApplicationId(applicationId)
      .then(res => {
        this.otherAkipEntities = res.data;
        this.otherAkipEntities.filter(otherAkipEntity => otherAkipEntity.id != this.akipEntity.id);
      });
  }
}
