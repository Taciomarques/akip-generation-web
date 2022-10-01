import { Component, Prop, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { AkipProcess } from '@/shared/model/akip-process.model';

@Component
export default class ShowAkipProcessComponent extends mixins(JhiDataUtils) {
  @Prop()
  akipProcessProp: AkipProcess;

  private akipProcess: AkipProcess = new AkipProcess();

  public collapseController: any = {
    showAkipProcess: true,
  };

  @Watch('akipProcessProp')
  onAkipProcessPropValueChange() {
    this.updateAkipProcess();
  }

  mounted() {
    this.updateAkipProcess();
  }

  public updateAkipProcess() {
    if (this.akipProcessProp) {
      this.akipProcess = this.akipProcessProp;
      if (!this.akipProcessProp.entities) {
        this.akipProcess.entities = [];
      }
    }
  }

  public collapse(collapseComponent: string): void {
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
  }
}
