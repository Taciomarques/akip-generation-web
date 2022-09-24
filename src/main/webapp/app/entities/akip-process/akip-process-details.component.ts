import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAkipProcess } from '@/shared/model/akip-process.model';
import AkipProcessService from './akip-process.service';

@Component
export default class AkipProcessDetails extends Vue {
  @Inject('akipProcessService') private akipProcessService: () => AkipProcessService;
  public akipProcess: IAkipProcess = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.akipProcessId) {
        vm.retrieveProcess(to.params.akipProcessId);
      }
    });
  }

  public retrieveProcess(processId) {
    this.akipProcessService()
      .find(processId)
      .then(res => {
        this.akipProcess = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
