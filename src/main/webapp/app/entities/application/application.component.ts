import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IApplication } from '@/shared/model/application.model';

import ApplicationService from './application.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Application extends Vue {
  @Inject('applicationService') private applicationService: () => ApplicationService;
  private removeId: number = null;

  public applications: IApplication[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllApplications();
  }

  public clear(): void {
    this.retrieveAllApplications();
  }

  public retrieveAllApplications(): void {
    this.isFetching = true;

    this.applicationService()
      .retrieve()
      .then(
        res => {
          this.applications = res.data;
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

  public prepareRemove(instance: IApplication): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeApplication(): void {
    this.applicationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('akipGenerationWebApp.application.deleted', { param: this.removeId });
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
}
