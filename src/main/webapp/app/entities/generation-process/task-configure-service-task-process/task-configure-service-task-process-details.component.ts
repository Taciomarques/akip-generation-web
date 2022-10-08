import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureServiceTaskProcessService from './task-configure-service-task-process.service';
import { TaskConfigureServiceTaskProcessContext } from './task-configure-service-task-process.model';

@Component
export default class TaskConfigureServiceTaskProcessDetailsComponent extends mixins(JhiDataUtils) {
  private taskConfigureServiceTaskProcessService: TaskConfigureServiceTaskProcessService = new TaskConfigureServiceTaskProcessService();
  private taskContext: TaskConfigureServiceTaskProcessContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskConfigureServiceTaskProcessService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
