import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureProcessBindingService from './task-configure-process-binding.service';
import { TaskConfigureProcessBindingContext } from './task-configure-process-binding.model';

@Component
export default class TaskConfigureProcessBindingDetailsComponent extends mixins(JhiDataUtils) {
  private taskConfigureProcessBindingService: TaskConfigureProcessBindingService = new TaskConfigureProcessBindingService();
  private taskContext: TaskConfigureProcessBindingContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskConfigureProcessBindingService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  get akipEntityDomain() {
    return this.taskContext.generationProcess.akipProcess.entities.find(entity => entity.type == 'DOMAIN');
  }

  get akipEntityProcessBinding() {
    return this.taskContext.generationProcess.akipProcess.entities.find(entity => entity.type == 'PROCESS_BINDING');
  }
}
