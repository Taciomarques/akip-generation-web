import Component from 'vue-class-component';
import { Prop, Vue } from 'vue-property-decorator';
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
}
