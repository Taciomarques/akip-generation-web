import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateStartFormProcessService from './task-generate-start-form-process.service';
import { TaskGenerateStartFormProcessContext } from './task-generate-start-form-process.model';

@Component
export default class TaskGenerateStartFormProcessDetailsComponent extends mixins(JhiDataUtils) {
  private taskGenerateStartFormProcessService: TaskGenerateStartFormProcessService = new TaskGenerateStartFormProcessService();
  private taskContext: TaskGenerateStartFormProcessContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskGenerateStartFormProcessService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
