import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAkipEntity } from '@/shared/model/akip-entity.model';
import AkipEntityService from './akip-entity.service';

@Component
export default class AkipEntityDetails extends Vue {
  @Inject('akipEntityService') private akipEntityService: () => AkipEntityService;
  public akipEntity: IAkipEntity = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.akipEntityId) {
        vm.retrieveEntity(to.params.akipEntityId);
      }
    });
  }

  public retrieveEntity(entidadeId) {
    this.akipEntityService()
      .find(entidadeId)
      .then(res => {
        this.akipEntity = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
