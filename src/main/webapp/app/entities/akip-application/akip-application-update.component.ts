import { Component, Vue, Inject } from 'vue-property-decorator';

import AkipEntityService from '@/entities/akip-entity/akip-entity.service';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

import AkipProcessService from '@/entities/akip-process/akip-process.service';
import { IAkipProcess } from '@/shared/model/akip-process.model';

import { IAkipApplication, AkipApplication } from '@/shared/model/akip-application.model';
import AkipApplicationService from './akip-application.service';
import { required } from 'vuelidate/lib/validators';

const validations: any = {
  akipApplication: {
    name: { required },
    repositoryName: { required },
    createDate: {},
    properties: {},
  },
};

@Component({
  validations,
})
export default class AkipApplicationUpdate extends Vue {
  @Inject('akipApplicationService') private akipApplicationService: () => AkipApplicationService;
  public akipApplication: IAkipApplication = new AkipApplication();

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.akipApplicationId) {
        vm.retrieveApplication(to.params.akipApplicationId);
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
    if (this.akipApplication.id) {
      this.akipApplicationService()
        .update(this.akipApplication)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.akipApplication.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.akipApplicationService()
        .create(this.akipApplication)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.akipApplication.created', { param: param.id });
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
    this.akipApplicationService()
      .find(applicationId)
      .then(res => {
        this.akipApplication = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
