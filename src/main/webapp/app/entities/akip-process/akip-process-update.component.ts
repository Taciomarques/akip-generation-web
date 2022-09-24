import { Component, Vue, Inject } from 'vue-property-decorator';

import AkipEntityService from '@/entities/akip-entity/akip-entity.service';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

import AkipApplicationService from '@/entities/akip-application/akip-application.service';
import { IAkipApplication } from '@/shared/model/akip-application.model';

import { IAkipProcess, AkipProcess } from '@/shared/model/akip-process.model';
import AkipProcessService from './akip-process.service';

const validations: any = {
  akipProcess: {
    name: {},
    percentageExecuted: {},
    status: {},
  },
};

@Component({
  validations,
})
export default class ProcessUpdate extends Vue {
  @Inject('akipProcessService') private akipProcessService: () => AkipProcessService;
  public akipProcess: IAkipProcess = new AkipProcess();

  @Inject('akipEntityService') private akipEntityService: () => AkipEntityService;

  public akipEntities: IAkipEntity[] = [];

  @Inject('akipApplicationService') private akipApplicationService: () => AkipApplicationService;

  public akipApplications: IAkipApplication[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.akipProcessId) {
        vm.retrieveProcess(to.params.akipProcessId);
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
    if (this.akipProcess.id) {
      this.akipProcessService()
        .update(this.akipProcess)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.akipProcess.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.akipProcessService()
        .create(this.akipProcess)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.akipProcess.created', { param: param.id });
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
    this.akipProcessService()
      .find(processId)
      .then(res => {
        this.akipProcess = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.akipEntityService()
      .retrieve()
      .then(res => {
        this.akipEntities = res.data;
      });
    this.akipApplicationService()
      .retrieve()
      .then(res => {
        this.akipApplications = res.data;
      });
  }
}
