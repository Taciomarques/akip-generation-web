import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureUserTaskProcessService from './task-configure-user-task-process.service';
import { TaskConfigureUserTaskProcessContext } from './task-configure-user-task-process.model';

@Component
export default class TaskConfigureUserTaskProcessDetailsComponent extends mixins(JhiDataUtils) {
  private taskConfigureUserTaskProcessService: TaskConfigureUserTaskProcessService = new TaskConfigureUserTaskProcessService();
  private taskContext: TaskConfigureUserTaskProcessContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskConfigureUserTaskProcessService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
