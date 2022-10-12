import Component from 'vue-class-component';
import { Prop, Vue } from 'vue-property-decorator';
import { AkipField } from '@/shared/model/akip-field.model';

@Component
export default class ShowAkipFieldsSelectComponent extends Vue {
  @Prop()
  public akipFields: AkipField[];

  @Prop({ default: false })
  readOnly: boolean;

  mounted() {
    this.akipFields = JSON.parse(JSON.stringify(this.akipFields));
  }
}
