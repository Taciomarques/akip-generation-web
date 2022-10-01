import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAkipProcess } from '@/shared/model/akip-process.model';

import AkipProcessService from './akip-process.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AkipProcessList extends Vue {
  @Inject('akipProcessService') private akipProcessService: () => AkipProcessService;

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

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
