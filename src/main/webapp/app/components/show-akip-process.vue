<template>
  <div>
    <div class="card" v-if="akipProcess">
      <h4 class="card-header collapse-link" v-on:click="collapse('showAkipProcess')">
        <div class="d-flex">
          <div class="p-1">
            <span class="title" v-text="$t('akipGenerationWebApp.akipProcess.detail.title')"> </span>
          </div>
          <div class="ml-3 small">
            <show-akip-process-status :value="akipProcess.status"></show-akip-process-status>
          </div>
          <div class="p-1 ml-3">
            <font-awesome-icon icon="compress-alt" v-if="collapseController.showAkipProcess"></font-awesome-icon>
            <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
          </div>
        </div>
      </h4>
      <b-collapse v-model="collapseController.showAkipProcess" id="collapse-akip-process">
        <div class="card-body">
          <div class="row">
            <div class="col">
              <div class="form-group input-group-sm">
                <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipProcess.name')" for="process-name">Name</label>
                <input type="text" class="form-control" name="name" readonly id="process-name" data-cy="name" v-model="akipProcess.name" />
              </div>
            </div>
            <div class="col">
              <div class="form-group input-group-sm">
                <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipProcess.application')" for="akip-process-application"
                  >Application Name</label
                >
                <input
                  type="text"
                  class="form-control"
                  name="application"
                  readonly
                  id="akip-process-application"
                  data-cy="application"
                  v-model="akipProcess.application.name"
                />
              </div>
            </div>
          </div>

          <div class="form-group">
            <label v-text="$t('akipGenerationWebApp.akipProcess.percentageExecuted')" />
            <div class="progress" style="border-radius: 5px">
              <div
                class="progress-bar progress-bar-striped progress-bar-animated"
                role="progressbar"
                :aria-valuenow="akipProcess.percentageExecuted"
                aria-valuemin="0"
                aria-valuemax="100"
                :style="'width: ' + akipProcess.percentageExecuted + '%;'"
              >
                {{ akipProcess.percentageExecuted ? akipProcess.percentageExecuted + '%' : '0%' }}
              </div>
            </div>
            <div class="form-group" v-if="akipProcess.bpmn">
              <label class="form-control-label mt-3" v-text="$t('akipGenerationWebApp.taskProvideProcessBpmn.bpmn')">bpmn</label>
              <div class="form-group input-group-sm d-flex mb-2">
                <input type="text" class="form-control" :value="akipProcess.bpmn.name" disabled />
                <button
                  type="button"
                  v-on:click="openFile(akipProcess.bpmn.specificationFileContentType, akipProcess.bpmn.specificationFile)"
                  class="btn btn-info btn-sm pull-right"
                >
                  <font-awesome-icon icon="folder-open"></font-awesome-icon>
                </button>
                <button
                  class="btn btn-primary btn-sm pull-right"
                  @click="
                    downloadFile(akipProcess.bpmn.specificationFileContentType, akipProcess.bpmn.specificationFile, akipProcess.bpmn.name)
                  "
                >
                  <font-awesome-icon icon="download"></font-awesome-icon>
                </button>
              </div>
            </div>
          </div>

          <div class="mt-3" v-if="akipEntityDomain">
            <hr />
            <show-akip-entity
              :akipEntityProp="akipEntityDomain"
              :readOnly="true"
              :typeEntity="akipEntityDomain.type"
              :applicationId="akipEntityDomain.application.id"
            ></show-akip-entity>
          </div>

          <div class="mt-3" v-if="akipEntityProcessBinding">
            <hr />
            <show-akip-entity
              :akipEntityProp="akipEntityProcessBinding"
              :readOnly="true"
              :typeEntity="akipEntityProcessBinding.type"
              :applicationId="akipEntityProcessBinding.application.id"
            ></show-akip-entity>
          </div>

          <div class="mt-3" v-if="akipEntityStartForm">
            <hr />
            <show-akip-entity
              :akipEntityProp="akipEntityStartForm"
              :readOnly="true"
              :typeEntity="akipEntityStartForm.type"
              :applicationId="akipEntityStartForm.application.id"
            ></show-akip-entity>
          </div>

          <div class="mt-3" v-for="akipEntity in akipProcess.entities.filter(akipEntity => akipEntity.type == 'USER_TASK')">
            <hr />
            <show-akip-entity
              :akipEntityProp="akipEntity"
              :readOnly="true"
              :typeEntity="akipEntity.type"
              :applicationId="akipEntity.application.id"
            ></show-akip-entity>
          </div>

          <hr />
          <ul class="list-group">
            <li
              class="list-group-item d-flex justify-content-between align-items-center"
              v-for="akipEntity in akipProcess.entities.filter(akipEntity => akipEntity.type == 'SERVICE_TASK')"
            >
              {{ akipEntity.name }}
              <show-akip-entity-type :value="akipEntity.type"></show-akip-entity-type>
            </li>
          </ul>
        </div>
      </b-collapse>
    </div>
  </div>
</template>

<script lang="ts" src="./show-akip-process.component.ts"></script>
