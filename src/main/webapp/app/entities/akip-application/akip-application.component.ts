import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAkipApplication } from '@/shared/model/akip-application.model';

import AkipApplicationService from './akip-application.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AkipApplication extends Vue {
  @Inject('akipApplicationService') private akipApplicationService: () => AkipApplicationService;
  private removeId: number = null;

  public akipApplications: IAkipApplication[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllApplications();
  }

  public clear(): void {
    this.retrieveAllApplications();
  }

  public retrieveAllApplications(): void {
    this.isFetching = true;

    this.akipApplicationService()
      .retrieve()
      .then(
        res => {
          this.akipApplications = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IAkipApplication): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeApplication(): void {
    this.akipApplicationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('akipGenerationWebApp.akipApplication.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllApplications();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
