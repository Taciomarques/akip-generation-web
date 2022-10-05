<template>
  <div>
    <div class="card mt-2">
      <div class="card-body">
        <div class="d-flex justify-content-between">
          <span class="card-title" v-text="$t('akipGenerationWebApp.akipField.field') + (index + 1)"></span>
          <div class="text-center" v-if="typeEntity == 'USER_TASK' || typeEntity == 'START_FORM'">
            <div class="form-group input-group-sm">
              <div class="custom-control custom-switch">
                <input
                  :disabled="readOnly"
                  @change="backupValuesInFieldValidateRules()"
                  type="checkbox"
                  class="custom-control-input"
                  name="akipField-fieldReadOnly"
                  id="akipField-fieldReadOnly"
                  data-cy="akipField-fieldReadOnly"
                  v-model="$v.akipField.fieldReadOnly.$model"
                />
                <label
                  class="custom-control-label"
                  v-text="$t('akipGenerationWebApp.akipField.fieldReadOnly')"
                  for="akipField-fieldReadOnly"
                  >fieldReadOnly</label
                >
              </div>
            </div>
          </div>
          <button v-if="!readOnly" v-on:click="removeFunction(index)" class="btn btn-danger btn-sm" data-cy="removeField">
            <font-awesome-icon icon="trash"></font-awesome-icon>
          </button>
        </div>

        <div class="form-group input-group-sm">
          <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipField.name')" for="akipField-fieldName">fieldName</label>
          <input
            type="text"
            class="form-control"
            :readonly="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
            name="akipFieldName"
            id="akipField-fieldName"
            data-cy="akipFieldName"
            :class="{ invalid: $v.akipField.fieldName.$invalid }"
            v-model="akipField.fieldName"
          />
        </div>

        <div class="row">
          <div class="col">
            <div class="form-group input-group-sm">
              <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipField.type')" for="akipField-fieldType"
                >akipFieldType</label
              >
              <select
                id="akipField-fieldType"
                :disabled="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                class="form-control"
                @change="resetValuesInFieldValidateRules()"
                :class="{ invalid: $v.fieldTypeTemp.$invalid }"
                v-model="fieldTypeTemp"
              >
                <option value="String" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.String')"></option>
                <option value="Integer" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.Integer')"></option>
                <option value="Long" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.Long')"></option>
                <option value="Float" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.Float')"></option>
                <option value="Double" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.Double')"></option>
                <option value="BigDecimal" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.BigDecimal')"></option>
                <option value="LocalDate" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.LocalDate')"></option>
                <option value="Instant" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.Instant')"></option>
                <option value="ZonedDateTime" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.ZonedDateTime')"></option>
                <option value="Duration" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.Duration')"></option>
                <option value="Boolean" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.Boolean')"></option>
                <option value="enum" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.enum')"></option>
                <option value="UUID" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.UUID')"></option>
                <option value="byte[]" v-text="$t('akipGenerationWebApp.akipField.fieldTypes.byte')"></option>
              </select>
            </div>
          </div>
          <div class="col" v-if="fieldTypeTemp == 'enum'">
            <div class="form-group input-group-sm">
              <div class="form-group input-group-sm">
                <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipField.enumType')" for="akipField-enumType"
                  >enumType</label
                >
                <input
                  id="akipField-fieldType"
                  :disabled="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                  class="form-control"
                  :class="{ invalid: $v.akipField.fieldType.$invalid }"
                  v-model="$v.akipField.fieldType.$model"
                />
              </div>
            </div>
          </div>
          <div class="col" v-if="fieldTypeTemp == 'byte[]'">
            <div class="form-group input-group-sm">
              <label
                class="form-control-label"
                v-text="$t('akipGenerationWebApp.akipField.fieldTypeBlobContent')"
                for="akipField-fieldTypeBlobContent"
                >fieldTypeBlobContent</label
              >
              <select
                id="akipField-fieldTypeBlobContent"
                :disabled="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                class="form-control"
                :class="{ invalid: $v.akipField.fieldTypeBlobContent.$invalid }"
                v-model="$v.akipField.fieldTypeBlobContent.$model"
              >
                <option value="image" v-text="$t('akipGenerationWebApp.akipField.blobContentTypes.image')"></option>
                <option value="text" v-text="$t('akipGenerationWebApp.akipField.blobContentTypes.text')"></option>
                <option value="any" v-text="$t('akipGenerationWebApp.akipField.blobContentTypes.any')"></option>
              </select>
            </div>
          </div>
        </div>
        <div class="row" v-if="fieldTypeTemp == 'enum'">
          <div class="col">
            <div class="form-group input-group-sm">
              <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipField.values')" for="akipField-fieldValues"
                >akipFieldValues</label
              >
              <textarea
                type="text"
                class="form-control"
                :readonly="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                name="akipFieldValues"
                id="akipField-fieldValues"
                data-cy="akipFieldValues"
                :class="{ invalid: $v.akipField.fieldValues.$invalid }"
                v-model="$v.akipField.fieldValues.$model"
              />
            </div>
          </div>
        </div>
        <div class="card">
          <div class="card-header">
            <b><span v-text="$t('akipGenerationWebApp.akipField.validations')" /></b>
          </div>
          <div class="card-body">
            <div class="row">
              <div class="col">
                <div class="form-group">
                  <div class="custom-control custom-switch">
                    <input
                      :disabled="readOnly"
                      type="checkbox"
                      @click="changeValueInFieldValidateRules('required')"
                      class="custom-control-input"
                      name="addRequiredInFieldValidateRules"
                      :id="'addRequiredInFieldValidateRules' + index"
                      data-cy="addRequiredInFieldValidateRules"
                      v-model="akipField.fieldValidateRules && akipField.fieldValidateRules.includes('required')"
                    />
                    <label
                      class="custom-control-label"
                      v-text="$t('akipGenerationWebApp.akipField.required')"
                      :for="'addRequiredInFieldValidateRules' + index"
                      >addRequiredInFieldValidateRules</label
                    >
                  </div>
                </div>
              </div>
              <div class="col">
                <div class="form-group">
                  <div class="custom-control custom-switch">
                    <input
                      :disabled="readOnly"
                      type="checkbox"
                      @click="changeValueInFieldValidateRules('unique')"
                      class="custom-control-input"
                      name="addUniqueInFieldValidateRules"
                      :id="'addUniqueInFieldValidateRules' + index"
                      data-cy="addUniqueInFieldValidateRules"
                      v-model="akipField.fieldValidateRules && akipField.fieldValidateRules.includes('unique')"
                    />
                    <label
                      class="custom-control-label"
                      v-text="$t('akipGenerationWebApp.akipField.unique')"
                      :for="'addUniqueInFieldValidateRules' + index"
                      >addUniqueInFieldValidateRules</label
                    >
                  </div>
                </div>
              </div>
            </div>
            <div
              class="row"
              v-if="
                akipField.fieldType == 'Integer' ||
                akipField.fieldType == 'Long' ||
                akipField.fieldType == 'Double' ||
                akipField.fieldType == 'BigDecimal' ||
                akipField.fieldType == 'Float'
              "
            >
              <div class="col">
                <div class="form-group">
                  <div class="custom-control custom-switch">
                    <input
                      :disabled="readOnly"
                      type="checkbox"
                      @click="changeValueInFieldValidateRules('min')"
                      class="custom-control-input"
                      name="addMinInFieldValidateRules"
                      :id="'addMinInFieldValidateRules' + index"
                      data-cy="addMinInFieldValidateRules"
                      v-model="akipField.fieldValidateRules && akipField.fieldValidateRules.includes('min')"
                    />
                    <label
                      class="custom-control-label"
                      v-text="$t('akipGenerationWebApp.akipField.min')"
                      :for="'addMinInFieldValidateRules' + index"
                      >addMinInFieldValidateRules</label
                    >
                  </div>
                </div>
              </div>

              <div class="col">
                <div
                  class="form-group input-group-sm"
                  v-if="
                    (akipField.fieldType == 'Integer' ||
                      akipField.fieldType == 'Long' ||
                      akipField.fieldType == 'Double' ||
                      akipField.fieldType == 'BigDecimal' ||
                      akipField.fieldType == 'Float') &&
                    akipField.fieldValidateRules &&
                    akipField.fieldValidateRules.includes('min')
                  "
                >
                  <div class="input-group input-group-sm">
                    <div class="input-group-prepend">
                      <span v-text="$t('akipGenerationWebApp.akipField.fieldValidateRulesMin')" class="input-group-text"></span>
                    </div>
                    <input
                      id="akipField-fieldValidateRulesMin"
                      :disabled="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                      :placeholder="0"
                      type="number"
                      class="form-control"
                      :class="{ invalid: $v.akipField.fieldValidateRulesMin.$invalid }"
                      v-model="$v.akipField.fieldValidateRulesMin.$model"
                    />
                  </div>
                </div>
              </div>

              <div class="col">
                <div class="form-group">
                  <div class="custom-control custom-switch">
                    <input
                      :disabled="readOnly"
                      type="checkbox"
                      @click="changeValueInFieldValidateRules('max')"
                      class="custom-control-input"
                      name="addMaxInFieldValidateRules"
                      :id="'addMaxInFieldValidateRules' + index"
                      data-cy="addMaxInFieldValidateRules"
                      v-model="akipField.fieldValidateRules && akipField.fieldValidateRules.includes('max')"
                    />
                    <label
                      class="custom-control-label"
                      v-text="$t('akipGenerationWebApp.akipField.max')"
                      :for="'addMaxInFieldValidateRules' + index"
                      >addMaxInFieldValidateRules</label
                    >
                  </div>
                </div>
              </div>
              <div class="col">
                <div
                  class="form-group input-group-sm"
                  v-if="
                    (akipField.fieldType == 'Integer' ||
                      akipField.fieldType == 'Long' ||
                      akipField.fieldType == 'Double' ||
                      akipField.fieldType == 'BigDecimal' ||
                      akipField.fieldType == 'Float') &&
                    akipField.fieldValidateRules &&
                    akipField.fieldValidateRules.includes('max')
                  "
                >
                  <div class="input-group input-group-sm">
                    <div class="input-group-prepend">
                      <span v-text="$t('akipGenerationWebApp.akipField.fieldValidateRulesMax')" class="input-group-text"></span>
                    </div>
                    <input
                      id="akipField-fieldValidateRulesMax"
                      :disabled="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                      :placeholder="100"
                      type="number"
                      class="form-control"
                      :class="{ invalid: $v.akipField.fieldValidateRulesMax.$invalid }"
                      v-model="$v.akipField.fieldValidateRulesMax.$model"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div
              class="row"
              v-if="akipField.fieldType == 'String' || (akipField.fieldType == 'byte[]' && akipField.fieldTypeBlobContent == 'text')"
            >
              <div class="col">
                <div class="form-group">
                  <div class="custom-control custom-switch">
                    <input
                      :disabled="readOnly"
                      type="checkbox"
                      @click="changeValueInFieldValidateRules('minlength')"
                      class="custom-control-input"
                      name="addMinLengthInFieldValidateRules"
                      :id="'addMinLengthInFieldValidateRules' + index"
                      data-cy="addMinLengthInFieldValidateRules"
                      v-model="akipField.fieldValidateRules && akipField.fieldValidateRules.includes('minlength')"
                    />
                    <label
                      class="custom-control-label"
                      v-text="$t('akipGenerationWebApp.akipField.minLength')"
                      :for="'addMinLengthInFieldValidateRules' + index"
                      >addMinLengthInFieldValidateRules</label
                    >
                  </div>
                </div>
              </div>
              <div class="col">
                <div
                  class="form-group input-group-sm"
                  v-if="
                    (akipField.fieldType == 'String' || (akipField.fieldType == 'byte[]' && akipField.fieldTypeBlobContent == 'text')) &&
                    akipField.fieldValidateRules &&
                    akipField.fieldValidateRules.includes('minlength')
                  "
                >
                  <div class="input-group input-group-sm">
                    <div class="input-group-prepend">
                      <span v-text="$t('akipGenerationWebApp.akipField.fieldValidateRulesMinLength')" class="input-group-text"></span>
                    </div>
                    <input
                      id="akipField-fieldValidateRulesMinLength"
                      :disabled="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                      :placeholder="0"
                      type="number"
                      class="form-control"
                      :class="{ invalid: $v.akipField.fieldValidateRulesMinlength.$invalid }"
                      v-model="$v.akipField.fieldValidateRulesMinlength.$model"
                    />
                  </div>
                </div>
              </div>

              <div class="col">
                <div class="form-group">
                  <div class="custom-control custom-switch">
                    <input
                      :disabled="readOnly"
                      type="checkbox"
                      @click="changeValueInFieldValidateRules('maxlength')"
                      class="custom-control-input"
                      name="addMaxLengthInFieldValidateRules"
                      :id="'addMaxLengthInFieldValidateRules' + index"
                      data-cy="addMaxLengthInFieldValidateRules"
                      v-model="akipField.fieldValidateRules && akipField.fieldValidateRules.includes('maxlength')"
                    />
                    <label
                      class="custom-control-label"
                      v-text="$t('akipGenerationWebApp.akipField.maxLength')"
                      :for="'addMaxLengthInFieldValidateRules' + index"
                      >addMaxLengthInFieldValidateRules</label
                    >
                  </div>
                </div>
              </div>
              <div class="col">
                <div
                  class="form-group input-group-sm"
                  v-if="
                    (akipField.fieldType == 'String' || (akipField.fieldType == 'byte[]' && akipField.fieldTypeBlobContent == 'text')) &&
                    akipField.fieldValidateRules &&
                    akipField.fieldValidateRules.includes('maxlength')
                  "
                >
                  <div class="input-group input-group-sm">
                    <div class="input-group-prepend">
                      <span v-text="$t('akipGenerationWebApp.akipField.fieldValidateRulesMaxLength')" class="input-group-text"></span>
                    </div>

                    <input
                      id="akipField-fieldValidateRulesMaxLength"
                      :disabled="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                      :placeholder="20"
                      type="number"
                      class="form-control"
                      :class="{ invalid: $v.akipField.fieldValidateRulesMaxlength.$invalid }"
                      v-model="$v.akipField.fieldValidateRulesMaxlength.$model"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div
              class="row"
              v-if="akipField.fieldType == 'String' || (akipField.fieldType == 'byte[]' && akipField.fieldTypeBlobContent == 'text')"
            >
              <div class="col">
                <div class="form-group">
                  <div class="custom-control custom-switch">
                    <input
                      :disabled="readOnly"
                      type="checkbox"
                      @click="changeValueInFieldValidateRules('pattern')"
                      class="custom-control-input"
                      name="addPatternInFieldValidateRules"
                      :id="'addPatternInFieldValidateRules' + index"
                      data-cy="addPatternInFieldValidateRules"
                      v-model="akipField.fieldValidateRules && akipField.fieldValidateRules.includes('pattern')"
                    />
                    <label
                      class="custom-control-label"
                      v-text="$t('akipGenerationWebApp.akipField.pattern')"
                      :for="'addPatternInFieldValidateRules' + index"
                      >addPatternInFieldValidateRules</label
                    >
                  </div>
                </div>
              </div>
              <div class="col">
                <div
                  class="form-group input-group-sm"
                  v-if="
                    (akipField.fieldType == 'String' || (akipField.fieldType == 'byte[]' && akipField.fieldTypeBlobContent == 'text')) &&
                    akipField.fieldValidateRules &&
                    akipField.fieldValidateRules.includes('pattern')
                  "
                >
                  <div class="input-group input-group-sm">
                    <div class="input-group-prepend">
                      <span v-text="$t('akipGenerationWebApp.akipField.fieldValidateRulesPattern')" class="input-group-text"></span>
                    </div>

                    <input
                      id="akipField-fieldValidateRulesPattern"
                      :disabled="readOnly || typeEntity == 'START_FORM' || typeEntity == 'PROCESS_BINDING'"
                      :placeholder="'^[a-zA-Z0-9]*$'"
                      type="text"
                      class="form-control"
                      :class="{ invalid: $v.akipField.fieldValidateRulesPattern.$invalid }"
                      v-model="$v.akipField.fieldValidateRulesPattern.$model"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./show-akip-field.component.ts"></script>
