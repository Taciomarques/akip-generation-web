import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProcess } from '@/shared/model/process.model';

import ProcessService from './process.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Process extends Vue {
  @Inject('processService') private processService: () => ProcessService;
  private removeId: number = null;

  public processes: IProcess[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProcesss();
  }

  public clear(): void {
    this.retrieveAllProcesss();
  }

  public retrieveAllProcesss(): void {
    this.isFetching = true;

    this.processService()
      .retrieve()
      .then(
        res => {
          this.processes = res.data;
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

  public prepareRemove(instance: IProcess): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProcess(): void {
    this.processService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('akipGenerationWebApp.process.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllProcesss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
