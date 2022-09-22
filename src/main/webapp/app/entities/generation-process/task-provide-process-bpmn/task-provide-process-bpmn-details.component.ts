import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskProvideProcessBpmnService from './task-provide-process-bpmn.service';
import { TaskProvideProcessBpmnContext } from './task-provide-process-bpmn.model';

@Component
export default class TaskProvideProcessBpmnDetailsComponent extends mixins(JhiDataUtils) {
  private taskProvideProcessBpmnService: TaskProvideProcessBpmnService = new TaskProvideProcessBpmnService();
  private taskContext: TaskProvideProcessBpmnContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskProvideProcessBpmnService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
