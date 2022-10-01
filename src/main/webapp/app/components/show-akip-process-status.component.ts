import Component from 'vue-class-component';
import { Prop, Vue } from 'vue-property-decorator';
import { StatusProcess } from '@/shared/model/enumerations/status-process.model';

@Component
export default class ShowAkipProcessStatusComponent extends Vue {
  @Prop()
  value: StatusProcess;
}
