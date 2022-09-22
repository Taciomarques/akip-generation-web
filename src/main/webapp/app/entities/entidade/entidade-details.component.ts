import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEntidade } from '@/shared/model/entidade.model';
import EntidadeService from './entidade.service';

@Component
export default class EntidadeDetails extends Vue {
  @Inject('entidadeService') private entidadeService: () => EntidadeService;
  public entidade: IEntidade = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entidadeId) {
        vm.retrieveEntidade(to.params.entidadeId);
      }
    });
  }

  public retrieveEntidade(entidadeId) {
    this.entidadeService()
      .find(entidadeId)
      .then(res => {
        this.entidade = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
