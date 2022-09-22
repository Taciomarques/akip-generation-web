import { Component, Vue, Inject } from 'vue-property-decorator';

import EntidadeService from '@/entities/entidade/entidade.service';
import { IEntidade } from '@/shared/model/entidade.model';

import ApplicationService from '@/entities/application/application.service';
import { IApplication } from '@/shared/model/application.model';

import { IProcess, Process } from '@/shared/model/process.model';
import ProcessService from './process.service';

const validations: any = {
  process: {
    name: {},
    percentageExecuted: {},
    status: {},
  },
};

@Component({
  validations,
})
export default class ProcessUpdate extends Vue {
  @Inject('processService') private processService: () => ProcessService;
  public process: IProcess = new Process();

  @Inject('entidadeService') private entidadeService: () => EntidadeService;

  public entidades: IEntidade[] = [];

  @Inject('applicationService') private applicationService: () => ApplicationService;

  public applications: IApplication[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processId) {
        vm.retrieveProcess(to.params.processId);
      }
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
    if (this.process.id) {
      this.processService()
        .update(this.process)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.process.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.processService()
        .create(this.process)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.process.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveProcess(processId): void {
    this.processService()
      .find(processId)
      .then(res => {
        this.process = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.entidadeService()
      .retrieve()
      .then(res => {
        this.entidades = res.data;
      });
    this.applicationService()
      .retrieve()
      .then(res => {
        this.applications = res.data;
      });
  }
}
