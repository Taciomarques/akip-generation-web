import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskProvideProcessBpmnService from './task-provide-process-bpmn.service';
import { TaskProvideProcessBpmnContext } from './task-provide-process-bpmn.model';

const validations: any = {
  taskContext: {
    generationProcess: {
      process: {
        bpmn: {},
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
}
