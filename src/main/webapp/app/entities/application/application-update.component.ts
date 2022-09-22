import { Component, Vue, Inject } from 'vue-property-decorator';

import EntidadeService from '@/entities/entidade/entidade.service';
import { IEntidade } from '@/shared/model/entidade.model';

import ProcessService from '@/entities/process/process.service';
import { IProcess } from '@/shared/model/process.model';

import { IApplication, Application } from '@/shared/model/application.model';
import ApplicationService from './application.service';

const validations: any = {
  application: {
    name: {},
    repositoryName: {},
    createDate: {},
    properties: {},
  },
};

@Component({
  validations,
})
export default class ApplicationUpdate extends Vue {
  @Inject('applicationService') private applicationService: () => ApplicationService;
  public application: IApplication = new Application();

  @Inject('entidadeService') private entidadeService: () => EntidadeService;

  public entidades: IEntidade[] = [];

  @Inject('processService') private processService: () => ProcessService;

  public processes: IProcess[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.applicationId) {
        vm.retrieveApplication(to.params.applicationId);
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
    if (this.application.id) {
      this.applicationService()
        .update(this.application)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.application.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.applicationService()
        .create(this.application)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.application.created', { param: param.id });
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

  public retrieveApplication(applicationId): void {
    this.applicationService()
      .find(applicationId)
      .then(res => {
        this.application = res;
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
    this.processService()
      .retrieve()
      .then(res => {
        this.processes = res.data;
      });
  }
}
