import { Component, Vue, Inject } from 'vue-property-decorator';

import ApplicationService from '@/entities/application/application.service'; //
import { IApplication } from '@/shared/model/application.model';

import { IGenerationProcess, GenerationProcess } from '@/shared/model/generation-process.model';

import { ProcessInstance, ProcessDefinitionService } from 'akip-vue-community';

import { Process } from '@/shared/model/process.model';
import GenerationProcessService from './generation-process.service';
import { required } from 'vuelidate/lib/validators';

const validations: any = {
  generationProcess: {
    process: {
      name: { required },
      application: { required },
    },
  },
};

@Component({
  validations,
})
export default class GenerationProcessStartFormInitComponent extends Vue {
  @Inject('generationProcessService') private generationProcessService: () => GenerationProcessService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId: string = 'GenerationProcess';
  public generationProcess: IGenerationProcess = new GenerationProcess();

  @Inject('applicationService') private applicationService: () => ApplicationService;

  public Applications: IApplication[] = [];

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initGenerationProcessStartForm();
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;

    this.generationProcessService()
      .create(this.generationProcess)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = this.$t('akipGenerationWebApp.generationProcessStartForm.created', { param: param.id });
        this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Success',
          variant: 'success',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

  public initGenerationProcessStartForm(): void {
    this.generationProcess.process = new Process();
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.processDefinitionService.find(this.bpmnProcessDefinitionId).then(res => {
      this.generationProcess.processInstance = new ProcessInstance();
      this.generationProcess.processInstance.processDefinition = res;
    });
    this.applicationService()
      .retrieve()
      .then(res => {
        this.Applications = res.data;
      });
  }
}
