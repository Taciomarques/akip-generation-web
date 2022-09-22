import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEntidade } from '@/shared/model/entidade.model';

import EntidadeService from './entidade.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Entidade extends Vue {
  @Inject('entidadeService') private entidadeService: () => EntidadeService;
  private removeId: number = null;

  public entidades: IEntidade[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEntidades();
  }

  public clear(): void {
    this.retrieveAllEntidades();
  }

  public retrieveAllEntidades(): void {
    this.isFetching = true;

    this.entidadeService()
      .retrieve()
      .then(
        res => {
          this.entidades = res.data;
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

  public prepareRemove(instance: IEntidade): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEntidade(): void {
    this.entidadeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('akipGenerationWebApp.entidade.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEntidades();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
