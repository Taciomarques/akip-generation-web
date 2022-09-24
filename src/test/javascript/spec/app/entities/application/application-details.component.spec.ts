/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ApplicationDetailComponent from '../../../../../../main/webapp/app/entities/akip-application/akip-application-details.vue';
import ApplicationClass from '../../../../../../main/webapp/app/entities/akip-application/akip-application-details.component';
import AkipApplicationService from '../../../../../../main/webapp/app/entities/akip-application/akip-application.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Application Management Detail Component', () => {
    let wrapper: Wrapper<ApplicationClass>;
    let comp: ApplicationClass;
    let applicationServiceStub: SinonStubbedInstance<AkipApplicationService>;

    beforeEach(() => {
      applicationServiceStub = sinon.createStubInstance<AkipApplicationService>(AkipApplicationService);

      wrapper = shallowMount<ApplicationClass>(ApplicationDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { akipApplicationService: () => applicationServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundApplication = { id: 123 };
        applicationServiceStub.find.resolves(foundApplication);

        // WHEN
        comp.retrieveApplication(123);
        await comp.$nextTick();

        // THEN
        expect(comp.application).toBe(foundApplication);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApplication = { id: 123 };
        applicationServiceStub.find.resolves(foundApplication);

        // WHEN
        comp.beforeRouteEnter({ params: { applicationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.application).toBe(foundApplication);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
