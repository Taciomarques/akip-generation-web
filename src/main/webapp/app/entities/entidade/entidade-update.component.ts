import { Component, Vue, Inject } from 'vue-property-decorator';

import ProcessService from '@/entities/process/process.service';
import { IProcess } from '@/shared/model/process.model';

import ApplicationService from '@/entities/application/application.service';
import { IApplication } from '@/shared/model/application.model';

import { IEntidade, Entidade } from '@/shared/model/entidade.model';
import EntidadeService from './entidade.service';

const validations: any = {
  entidade: {
    name: {},
    fields: {},
    relations: {},
    type: {},
  },
};

@Component({
  validations,
})
export default class EntidadeUpdate extends Vue {
  @Inject('entidadeService') private entidadeService: () => EntidadeService;
  public entidade: IEntidade = new Entidade();

  @Inject('processService') private processService: () => ProcessService;

  public processes: IProcess[] = [];

  @Inject('applicationService') private applicationService: () => ApplicationService;

  public applications: IApplication[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entidadeId) {
        vm.retrieveEntidade(to.params.entidadeId);
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
    if (this.entidade.id) {
      this.entidadeService()
        .update(this.entidade)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.entidade.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.entidadeService()
        .create(this.entidade)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.entidade.created', { param: param.id });
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

  public retrieveEntidade(entidadeId): void {
    this.entidadeService()
      .find(entidadeId)
      .then(res => {
        this.entidade = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.processService()
      .retrieve()
      .then(res => {
        this.processes = res.data;
      });
    this.applicationService()
      .retrieve()
      .then(res => {
        this.applications = res.data;
      });
  }
}
