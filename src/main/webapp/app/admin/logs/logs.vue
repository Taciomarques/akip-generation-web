<template>
  <div class="card border-primary">
    <div class="card-header">
      <h2 id="logs-page-heading" v-text="$t('logs.title')" data-cy="logsPageHeading">Logs</h2>
      <p v-text="$t('logs.nbloggers', { total: loggers.length })">There are {{ loggers.length }} loggers.</p>
    </div>
    <div class="card-body">
      <div class="table-responsive">
        <div v-if="loggers">
          <span v-text="$t('logs.filter')">Filter</span> <input type="text" v-model="filtered" class="form-control" />

          <table class="table table-sm table-striped table-bordered" aria-describedby="Logs">
            <thead>
              <tr title="click to order">
                <th v-on:click="changeOrder('name')" scope="col"><span v-text="$t('logs.table.name')">Name</span></th>
                <th v-on:click="changeOrder('level')" scope="col"><span v-text="$t('logs.table.level')">Level</span></th>
              </tr>
            </thead>

            <tr v-for="logger in orderBy(filterBy(loggers, filtered), orderProp, reverse === true ? 1 : -1)" :key="logger.name">
              <td>
                <small>{{ logger.name }}</small>
              </td>
              <td>
                <button
                  v-on:click="updateLevel(logger.name, 'TRACE')"
                  :class="logger.level === 'TRACE' ? 'btn-primary' : 'btn-light'"
                  class="btn btn-sm"
                >
                  TRACE
                </button>
                <button
                  v-on:click="updateLevel(logger.name, 'DEBUG')"
                  :class="logger.level === 'DEBUG' ? 'btn-success' : 'btn-light'"
                  class="btn btn-sm"
                >
                  DEBUG
                </button>
                <button
                  v-on:click="updateLevel(logger.name, 'INFO')"
                  :class="logger.level === 'INFO' ? 'btn-info' : 'btn-light'"
                  class="btn btn-sm"
                >
                  INFO
                </button>
                <button
                  v-on:click="updateLevel(logger.name, 'WARN')"
                  :class="logger.level === 'WARN' ? 'btn-warning' : 'btn-light'"
                  class="btn btn-sm"
                >
                  WARN
                </button>
                <button
                  v-on:click="updateLevel(logger.name, 'ERROR')"
                  :class="logger.level === 'ERROR' ? 'btn-danger' : 'btn-light'"
                  class="btn btn-sm"
                >
                  ERROR
                </button>
                <button
                  v-on:click="updateLevel(logger.name, 'OFF')"
                  :class="logger.level === 'OFF' ? 'btn-secondary' : 'btn-light'"
                  class="btn btn-sm"
                >
                  OFF
                </button>
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    <div class="card-footer">
      <div class="container">
        <button type="submit" v-on:click.prevent="$router.go(-1)" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./logs.component.ts"></script>
