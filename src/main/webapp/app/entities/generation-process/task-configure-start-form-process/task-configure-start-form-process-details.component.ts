import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureStartFormProcessService from './task-configure-start-form-process.service';
import { TaskConfigureStartFormProcessContext } from './task-configure-start-form-process.model';

@Component
export default class TaskConfigureStartFormProcessDetailsComponent extends mixins(JhiDataUtils) {
  private taskConfigureStartFormProcessService: TaskConfigureStartFormProcessService = new TaskConfigureStartFormProcessService();
  private taskContext: TaskConfigureStartFormProcessContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskConfigureStartFormProcessService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
