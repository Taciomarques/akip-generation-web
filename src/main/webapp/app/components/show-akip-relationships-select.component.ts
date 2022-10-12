import Component from 'vue-class-component';
import { Prop, Vue } from 'vue-property-decorator';
import { AkipField } from '@/shared/model/akip-field.model';
import { AkipRelationship } from '@/shared/model/akip-relationship.model';

@Component
export default class ShowAkipRelationshipsSelectComponent extends Vue {
  @Prop()
  public akipRelationships: AkipRelationship[];

  @Prop({ default: false })
  readOnly: boolean;

  mounted() {
    this.akipRelationships = JSON.parse(JSON.stringify(this.akipRelationships));
  }

  public selectAllAkipRelationships() {
    this.akipRelationships.forEach(akipField => {
      this.$set(akipField, 'selected', akipField['selected'] == undefined ? true : !akipField['selected']);
    });
  }

  get selectedAllAkipRelationships() {
    return this.akipRelationships.every(akipField => akipField['selected']);
  }
}
