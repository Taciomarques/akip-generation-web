import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateUserTaskProcessService from './task-generate-user-task-process.service';
import { TaskGenerateUserTaskProcessContext } from './task-generate-user-task-process.model';

@Component
export default class TaskGenerateUserTaskProcessDetailsComponent extends mixins(JhiDataUtils) {
  private taskGenerateUserTaskProcessService: TaskGenerateUserTaskProcessService = new TaskGenerateUserTaskProcessService();
  private taskContext: TaskGenerateUserTaskProcessContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskGenerateUserTaskProcessService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
