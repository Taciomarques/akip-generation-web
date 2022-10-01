import Component from 'vue-class-component';
import { Prop, Vue } from 'vue-property-decorator';
import { TypeEntity } from '@/shared/model/enumerations/type-entity.model';

@Component
export default class ShowAkipEntityTypeComponent extends Vue {
  @Prop()
  value: TypeEntity;
}
