import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProcess } from '@/shared/model/process.model';
import ProcessService from './process.service';

@Component
export default class ProcessDetails extends Vue {
  @Inject('processService') private processService: () => ProcessService;
  public process: IProcess = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processId) {
        vm.retrieveProcess(to.params.processId);
      }
    });
  }

  public retrieveProcess(processId) {
    this.processService()
      .find(processId)
      .then(res => {
        this.process = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
