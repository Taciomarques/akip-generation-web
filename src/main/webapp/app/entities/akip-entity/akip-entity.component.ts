import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

import AkipEntityService from './akip-entity.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AkipEntity extends Vue {
  @Inject('akipEntityService') private akipEntityService: () => AkipEntityService;
  private removeId: number = null;

  public akipEntities: IAkipEntity[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEntities();
  }

  public clear(): void {
    this.retrieveAllEntities();
  }

  public retrieveAllEntities(): void {
    this.isFetching = true;

    this.akipEntityService()
      .retrieve()
      .then(
        res => {
          this.akipEntities = res.data;
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

  public prepareRemove(instance: IAkipEntity): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEntity(): void {
    this.akipEntityService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('akipGenerationWebApp.akipEntity.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEntities();
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
