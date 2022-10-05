import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskProvideProcessBpmnService from './task-provide-process-bpmn.service';
import { TaskProvideProcessBpmnContext } from './task-provide-process-bpmn.model';
import { required } from 'vuelidate/lib/validators';
import { Attachment, IAttachment } from '@/shared/model/attachment.model';

const validations: any = {
  taskContext: {
    generationProcess: {
      akipProcess: {
        bpmn: {
          required,
        },
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskProvideProcessBpmnExecuteComponent extends mixins(JhiDataUtils) {
  private taskProvideProcessBpmnService: TaskProvideProcessBpmnService = new TaskProvideProcessBpmnService();
  private taskContext: TaskProvideProcessBpmnContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskProvideProcessBpmnService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskProvideProcessBpmnService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}

  public setAttachment(event) {
    const files = event.target.files;
    for (const file in files) {
      if (files[file] instanceof File) {
        this.toBase64(files[file], base64Data => {
          const attachment: IAttachment = new Attachment();
          attachment.specificationFile = base64Data;
          attachment.specificationFileContentType = files[file].type;
          attachment.name = files[file].name;
          this.$set(this.taskContext.generationProcess.akipProcess, 'bpmn', attachment);
        });
      }
    }
  }

  public removeAttachment(): void {
    this.taskContext.generationProcess.akipProcess.bpmn = null;
  }
}
