import { Component, Prop, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { AkipProcess } from '@/shared/model/akip-process.model';
import { AkipEntity } from '../shared/model/akip-entity.model';

@Component
export default class ShowAkipProcessComponent extends mixins(JhiDataUtils) {
  @Prop()
  akipProcessProp: AkipProcess;

  private akipProcess: AkipProcess = new AkipProcess();

  private akipEntityDomain: AkipEntity = new AkipEntity();

  private akipEntityProcessBinding: AkipEntity = new AkipEntity();

  private akipEntityStartForm: AkipEntity = new AkipEntity();

  public collapseController: any = {
    showAkipProcess: true,
    showAttachments: true,
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

      this.akipEntityDomain = this.akipProcess.entities.find(akipEntity => akipEntity.type == 'DOMAIN');
      this.akipEntityProcessBinding = this.akipProcess.entities.find(akipEntity => akipEntity.type == 'PROCESS_BINDING');
      this.akipEntityStartForm = this.akipProcess.entities.find(akipEntity => akipEntity.type == 'START_FORM');
    }
  }

  public collapse(collapseComponent: string): void {
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
  }
}
