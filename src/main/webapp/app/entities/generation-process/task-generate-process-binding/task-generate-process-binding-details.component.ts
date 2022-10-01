import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateProcessBindingService from './task-generate-process-binding.service';
import { TaskGenerateProcessBindingContext } from './task-generate-process-binding.model';

@Component
export default class TaskGenerateProcessBindingDetailsComponent extends mixins(JhiDataUtils) {
  private taskGenerateProcessBindingService: TaskGenerateProcessBindingService = new TaskGenerateProcessBindingService();
  private taskContext: TaskGenerateProcessBindingContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskGenerateProcessBindingService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  get getDomainEntityInAkipProcess() {
    return this.taskContext.generationProcess.akipProcess.entities.find(entity => entity.type == 'DOMAIN');
  }

  get getProcessBindingInAkipProcess() {
    return this.taskContext.generationProcess.akipProcess.entities.find(entity => entity.type == 'PROCESS_BINDING');
  }
}
