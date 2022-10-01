import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { AkipProcess, IAkipProcess } from '@/shared/model/akip-process.model';

import AkipProcessService from './akip-process.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AkipProcessList extends Vue {
  @Inject('akipProcessService') private akipProcessService: () => AkipProcessService;
  private removeId: number = null;

  public akipProcesses: IAkipProcess[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProcesss();
  }

  public clear(): void {
    this.retrieveAllProcesss();
  }

  public retrieveAllProcesss(): void {
    this.isFetching = true;

    this.akipProcessService()
      .retrieve()
      .then(
        res => {
          this.akipProcesses = res.data;
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

  public prepareRemove(instance: IAkipProcess): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProcess(): void {
    this.akipProcessService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('akipGenerationWebApp.akipProcess.deleted', { param: this.removeId });
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

  public previousState(): void {
    this.$router.go(-1);
  }
}
