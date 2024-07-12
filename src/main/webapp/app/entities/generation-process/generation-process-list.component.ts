import { Component, Vue, Inject } from 'vue-property-decorator';
import { IGenerationProcess } from '@/shared/model/generation-process.model';

import { ProcessDefinition, ProcessDefinitionService } from 'akip-vue-community';

import GenerationProcessService from './generation-process.service';

@Component
export default class GenerationProcessListComponent extends Vue {
  @Inject('generationProcessService') private generationProcessService: () => GenerationProcessService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId = 'GenerationProcess';
  public processDefinition: ProcessDefinition = new ProcessDefinition();
  public generationProcessList: IGenerationProcess[] = [];

  public isFetchingProcessDefinition = false;
  public isFetchingProcessInstances = false;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.retrieveProcessDefinition();
    this.retrieveProcessInstances();
  }

  public retrieveProcessDefinition() {
    this.isFetchingProcessDefinition = true;
    this.processDefinitionService.find(this.bpmnProcessDefinitionId).then(
      res => {
        this.processDefinition = res;
        this.isFetchingProcessDefinition = false;
      },
      err => {
        this.isFetchingProcessDefinition = false;
      }
    );
  }

  public retrieveProcessInstances(): void {
    this.isFetchingProcessInstances = true;
    this.generationProcessService()
      .retrieve()
      .then(
        res => {
          this.generationProcessList = res.data;
          this.isFetchingProcessInstances = false;
        },
        err => {
          this.isFetchingProcessInstances = false;
        }
      );
  }

  get isFetching(): boolean {
    return this.isFetchingProcessDefinition && this.isFetchingProcessInstances;
  }

  public handleSyncList(): void {
    this.retrieveProcessInstances();
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
