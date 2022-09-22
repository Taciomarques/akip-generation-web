import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGenerationProcess } from '@/shared/model/generation-process.model';
import GenerationProcessService from './generation-process.service';

@Component
export default class GenerationProcessDetailsComponent extends Vue {
  @Inject('generationProcessService') private generationProcessService: () => GenerationProcessService;
  public generationProcess: IGenerationProcess = {};

  public isFetching: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processInstanceId) {
        vm.retrieveGenerationProcess(to.params.processInstanceId);
      }
    });
  }

  public retrieveGenerationProcess(generationProcessId) {
    this.isFetching = true;
    this.generationProcessService()
      .find(generationProcessId)
      .then(
        res => {
          this.generationProcess = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public previousState() {
    this.$router.go(-1);
  }
}
