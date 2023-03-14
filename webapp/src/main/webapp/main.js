(self["webpackChunkblomst"] = self["webpackChunkblomst"] || []).push([["main"], {

    /***/ 7386:
    /*!*************************************************!*\
  !*** ./src/app/app-routing-strategy.service.ts ***!
  \*************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppRoutingStrategyService": () => (/* binding */ AppRoutingStrategyService)
            /* harmony export */
        });
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);


        class AppRoutingStrategyService extends _angular_router__WEBPACK_IMPORTED_MODULE_0__.BaseRouteReuseStrategy {
            shouldReuseRoute(future, curr) {
                return false;
            }
        }

        AppRoutingStrategyService.ɵfac = /*@__PURE__*/function () {
            let ɵAppRoutingStrategyService_BaseFactory;
            return function AppRoutingStrategyService_Factory(t) {
                return (ɵAppRoutingStrategyService_BaseFactory || (ɵAppRoutingStrategyService_BaseFactory = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵgetInheritedFactory"](AppRoutingStrategyService)))(t || AppRoutingStrategyService);
            };
        }();
        AppRoutingStrategyService.ɵprov = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineInjectable"]({
            token: AppRoutingStrategyService,
            factory: AppRoutingStrategyService.ɵfac,
            providedIn: 'root'
        });

        /***/
    }),

    /***/ 158:
    /*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppRoutingModule": () => (/* binding */ AppRoutingModule)
            /* harmony export */
        });
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var _navigator_app_navigator_resolver__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./navigator/app-navigator.resolver */ 3854);
        /* harmony import */
        var _app_resolver__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./app.resolver */ 8946);
        /* harmony import */
        var _social_timeline_timeline_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./social/timeline/timeline.component */ 4639);
        /* harmony import */
        var _security_register_register_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./security/register/register.component */ 7697);
        /* harmony import */
        var _security_logout_logout_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./security/logout/logout.component */ 3531);
        /* harmony import */
        var _security_login_login_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./security/login/login.component */ 3035);
        /* harmony import */
        var _navigator_form_form_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./navigator/form/form.component */ 3135);
        /* harmony import */
        var _navigator_table_table_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./navigator/table/table.component */ 3530);
        /* harmony import */
        var _control_user_user_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./control/user/user.component */ 1355);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/core */ 2560);


        const routes = [{
            path: "timeline",
            component: _social_timeline_timeline_component__WEBPACK_IMPORTED_MODULE_2__.TimelineComponent
        }, {
            path: "security/register",
            component: _security_register_register_component__WEBPACK_IMPORTED_MODULE_3__.RegisterComponent,
            resolve: {
                model: _app_resolver__WEBPACK_IMPORTED_MODULE_1__.AppResolver
            },
            data: {
                urls: [{
                    name: "model",
                    value: "service/security/register"
                }]
            }
        }, {
            path: "security/logout",
            component: _security_logout_logout_component__WEBPACK_IMPORTED_MODULE_4__.LogoutComponent
        }, {
            path: "security/login",
            component: _security_login_login_component__WEBPACK_IMPORTED_MODULE_5__.LoginComponent,
            resolve: {
                model: _app_resolver__WEBPACK_IMPORTED_MODULE_1__.AppResolver
            },
            data: {
                urls: [{
                    name: "model",
                    value: "service/security/login"
                }]
            }
        }, {
            path: "navigator/form",
            runGuardsAndResolvers: 'always',
            component: _navigator_form_form_component__WEBPACK_IMPORTED_MODULE_6__.FormComponent,
            resolve: {
                model: _navigator_app_navigator_resolver__WEBPACK_IMPORTED_MODULE_0__.AppNavigatorResolver
            }
        }, {
            path: "navigator/table",
            component: _navigator_table_table_component__WEBPACK_IMPORTED_MODULE_7__.TableComponent
        }, {
            path: ":user",
            component: _control_user_user_component__WEBPACK_IMPORTED_MODULE_8__.UserComponent,
            resolve: {
                model: _app_resolver__WEBPACK_IMPORTED_MODULE_1__.AppResolver
            },
            data: {
                urls: [{
                    name: "user",
                    value: "service/control/users/user/{user}"
                }]
            }
        }];

        class AppRoutingModule {
        }

        AppRoutingModule.ɵfac = function AppRoutingModule_Factory(t) {
            return new (t || AppRoutingModule)();
        };
        AppRoutingModule.ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_9__["ɵɵdefineNgModule"]({
            type: AppRoutingModule
        });
        AppRoutingModule.ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_9__["ɵɵdefineInjector"]({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_10__.RouterModule.forRoot(routes), _angular_router__WEBPACK_IMPORTED_MODULE_10__.RouterModule]
        });
        (function () {
            (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_9__["ɵɵsetNgModuleScope"](AppRoutingModule, {
                imports: [_angular_router__WEBPACK_IMPORTED_MODULE_10__.RouterModule],
                exports: [_angular_router__WEBPACK_IMPORTED_MODULE_10__.RouterModule]
            });
        })();

        /***/
    }),

    /***/ 9609:
    /*!****************************************!*\
  !*** ./src/app/app-startup.service.ts ***!
  \****************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppStartupService": () => (/* binding */ AppStartupService)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ 124);


        class AppStartupService {
            constructor(router) {
                this.router = router;
                window.secureFetch = function (url, method = "GET", data) {
                    return new Promise((resolve, reject) => {
                        let options = {
                            method: method
                        };
                        if (data) {
                            Object.assign(options, {
                                body: JSON.stringify(data),
                                headers: {
                                    "Content-Type": "application/json"
                                }
                            });
                        }
                        fetch(url, options).then(response => {
                            if (response.ok) {
                                resolve(response);
                            } else {
                                switch (response.status) {
                                    case 401:
                                        router.navigate(["/security/login"]);
                                        break;
                                    default:
                                        console.log('Some error occured');
                                        break;
                                }
                                reject(response);
                            }
                        });
                    });
                };
            }

            init() {
                return secureFetch("service")
                    .then(response => {
                        this.model = response;
                    });
            }
        }

        AppStartupService.ɵfac = function AppStartupService_Factory(t) {
            return new (t || AppStartupService)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵinject"](_angular_router__WEBPACK_IMPORTED_MODULE_1__.Router));
        };
        AppStartupService.ɵprov = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjectable"]({
            token: AppStartupService,
            factory: AppStartupService.ɵfac,
            providedIn: 'root'
        });

        /***/
    }),

    /***/ 8941:
    /*!********************************!*\
  !*** ./src/app/app.classes.ts ***!
  \********************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppView": () => (/* binding */ AppView)
            /* harmony export */
        });
        /* harmony import */
        var _shared_visibility_visibility_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./shared/visibility/visibility.component */ 8049);

        class AppView {
            constructor(route, windowManager) {
                this.route = route;
                this.windowManager = windowManager;
                let data = route.data;
                let jsons = data.value["urls"];
                let self = this;
                if (jsons) {
                    jsons.forEach((json, index) => {
                        let property = json["name"];
                        self[property] = data.value["model"][index];
                    });
                }
            }

            onVisibilityColumnClick(event, property, model) {
                event.stopPropagation();
                let url = `service/shared/visibility/column?property=${property}&class=${model["@type"]}&id=${model.id}`;
                secureFetch(url).then(response => response.json()).then(response => {
                    let windowRef = this.windowManager.create(_shared_visibility_visibility_component__WEBPACK_IMPORTED_MODULE_0__.VisibilityComponent, {
                        header: "Visibility",
                        width: "300px"
                    });
                    windowRef.instance.property = property;
                    windowRef.instance.clazz = model["@type"];
                    windowRef.instance.id = model.id;
                    windowRef.instance.model = response;
                });
            }

            onVisibilityRowClick(event, model) {
                event.stopPropagation();
                let url = `service/shared/visibility/row?class=${model["@type"]}&id=${model.id}`;
                secureFetch(url).then(response => response.json()).then(response => {
                    let windowRef = this.windowManager.create(_shared_visibility_visibility_component__WEBPACK_IMPORTED_MODULE_0__.VisibilityComponent, {
                        header: "Visibility",
                        width: "300px"
                    });
                    windowRef.instance.clazz = model["@type"];
                    windowRef.instance.id = model.id;
                    windowRef.instance.model = response;
                });
            }
        }

        /***/
    }),

    /***/ 5041:
    /*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppComponent": () => (/* binding */ AppComponent)
            /* harmony export */
        });
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _control_settings_settings_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./control/settings/settings.component */ 3475);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var _navigator_app_navigator_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./navigator/app-navigator.service */ 1341);
        /* harmony import */
        var _app_startup_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app-startup.service */ 9609);
        /* harmony import */
        var _angular_common__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/common */ 4666);


        function AppComponent_a_3_Template(rf, ctx) {
            if (rf & 1) {
                const _r4 = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](0, "a", 13);
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵlistener"]("click", function AppComponent_a_3_Template_a_click_0_listener() {
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵrestoreView"](_r4);
                    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵnextContext"]();
                    return _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵresetView"](ctx_r3.open = !ctx_r3.open);
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵtext"](1, "home");
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]();
            }
        }

        function AppComponent_img_8_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelement"](0, "img", 14);
            }
            if (rf & 2) {
                const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵproperty"]("src", ctx_r1.image, _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵsanitizeUrl"]);
            }
        }

        const _c0 = function (a0) {
            return [a0];
        };
        const _c1 = function (a0) {
            return {
                link: a0
            };
        };

        function AppComponent_li_14_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](0, "li")(1, "a", 15);
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵtext"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]()();
            }
            if (rf & 2) {
                const entry_r5 = ctx.$implicit;
                const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵproperty"]("routerLink", _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵpureFunction1"](3, _c0, "/navigator/" + entry_r5.value.type))("queryParams", _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵpureFunction1"](5, _c1, ctx_r2.toBase64(entry_r5.value.url)));
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵtextInterpolate"](entry_r5.key);
            }
        }

        class AppComponent extends ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AppMain {
            constructor(router, navigator, startUp, changeDetectionRef, windowManager, contextManager, injector, application) {
                super(windowManager, contextManager, injector, application);
                this.router = router;
                this.navigator = navigator;
                this.startUp = startUp;
                this.changeDetectionRef = changeDetectionRef;
                this.open = true;
                this.name = "Blomst";
                this.originalOrder = (a, b) => {
                    return 0;
                };
                if (window.location.host.indexOf("poseidon") > -1) {
                    this.name = "Poseidon";
                }
                let title = document.querySelector("title");
                if (title) {
                    title.textContent = this.name;
                }
                if (!this.isLoggedIn) {
                    router.navigate(["/security/login"]);
                }
                let matchMedia = window.matchMedia("(max-width: 800px)");
                this.open = !matchMedia.matches;
            }

            get isLoggedIn() {
                return this.startUp.model.$schema.links.logout;
            }

            get viewport() {
                return this.viewPort;
            }

            ngAfterViewInit() {
                this.initialize();
            }

            get image() {
                return this.startUp.model.form.picture?.url;
            }

            get links() {
                return this.navigator.links;
            }

            toBase64(value) {
                return btoa(value);
            }

            onUserSettings(event) {
                event.stopPropagation();
                let options = {
                    header: "Setting",
                    top: "1px",
                    right: "10px",
                    draggable: false,
                    resizeable: false,
                    width: "300px",
                    height: "200px"
                };
                let windowRef = this.windowManager.create(_control_settings_settings_component__WEBPACK_IMPORTED_MODULE_0__.SettingsComponent, options);
                return false;
            }
        }

        AppComponent.ɵfac = function AppComponent_Factory(t) {
            return new (t || AppComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_5__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](_navigator_app_navigator_service__WEBPACK_IMPORTED_MODULE_1__.AppNavigatorService), _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](_app_startup_service__WEBPACK_IMPORTED_MODULE_2__.AppStartupService), _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_3__.ChangeDetectorRef), _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.WindowManagerService), _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.ContextManagerService), _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_3__.Injector), _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_3__.ApplicationRef));
        };
        AppComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdefineComponent"]({
            type: AppComponent,
            selectors: [["app-root"]],
            viewQuery: function AppComponent_Query(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵviewQuery"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsViewportComponent, 5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵviewQuery"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsScrollAreaComponent, 5);
                }
                if (rf & 2) {
                    let _t;
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵloadQuery"]()) && (ctx.viewPort = _t.first);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵloadQuery"]()) && (ctx.scroll = _t.first);
                }
            },
            features: [_angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵInheritDefinitionFeature"]],
            decls: 21,
            vars: 8,
            consts: [[2, "height", "38px"], [2, "box-shadow", "0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)", "position", "absolute", "z-index", "9999"], ["left", "", 2, "display", "flex"], ["class", "material-icons", "style", "vertical-align: 4px", 3, "click", 4, "ngIf"], ["middle", ""], ["right", ""], [2, "cursor", "pointer", 3, "click"], ["style", "height: 32px; width: 32px;border-radius: 50%", 3, "src", 4, "ngIf"], [2, "position", "relative", "height", "100%"], [2, "height", "calc(100% - 38px)"], [3, "open"], [4, "ngFor", "ngForOf"], ["left", ""], [1, "material-icons", 2, "vertical-align", "4px", 3, "click"], [2, "height", "32px", "width", "32px", "border-radius", "50%", 3, "src"], [3, "routerLink", "queryParams"]],
            template: function AppComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelement"](0, "div", 0);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](1, "as-toolbar", 1)(2, "div", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵtemplate"](3, AppComponent_a_3_Template, 2, 0, "a", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](4, "div", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵtext"](5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](6, "div", 5)(7, "a", 6);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵlistener"]("click", function AppComponent_Template_a_click_7_listener($event) {
                        return ctx.onUserSettings($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵtemplate"](8, AppComponent_img_8_Template, 1, 1, "img", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](9, "as-viewport", 8)(10, "as-drawer-container", 9)(11, "as-drawer", 10)(12, "nav")(13, "ul");
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵtemplate"](14, AppComponent_li_14_Template, 3, 7, "li", 11);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵpipe"](15, "keyvalue");
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](16, "as-drawer-content")(17, "as-scroll-area");
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelement"](18, "router-outlet");
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]()()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](19, "as-footer");
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelement"](20, "as-taskbar", 12);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵproperty"]("ngIf", ctx.isLoggedIn);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵtextInterpolate"](ctx.name);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵproperty"]("ngIf", ctx.image);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵproperty"]("open", ctx.open && ctx.isLoggedIn);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵproperty"]("ngForOf", _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵpipeBind2"](15, 5, ctx.links, ctx.originalOrder));
                }
            },
            dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_6__.NgForOf, _angular_common__WEBPACK_IMPORTED_MODULE_6__.NgIf, _angular_router__WEBPACK_IMPORTED_MODULE_5__.RouterOutlet, _angular_router__WEBPACK_IMPORTED_MODULE_5__.RouterLink, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsToolbarComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsScrollAreaComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsDrawerComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsDrawerContainerComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsDrawerContentComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsFooterComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsTaskbarComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsViewportComponent, _angular_common__WEBPACK_IMPORTED_MODULE_6__.KeyValuePipe],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 6747:
    /*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppModule": () => (/* binding */ AppModule)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_25__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_27__ = __webpack_require__(/*! @angular/platform-browser */ 4497);
        /* harmony import */
        var _app_routing_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app-routing.module */ 158);
        /* harmony import */
        var _app_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./app.component */ 5041);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_28__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_26__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var _app_routing_strategy_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app-routing-strategy.service */ 7386);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_29__ = __webpack_require__(/*! @angular/forms */ 2508);
        /* harmony import */
        var _app_startup_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./app-startup.service */ 9609);
        /* harmony import */
        var _social_timeline_timeline_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./social/timeline/timeline.component */ 4639);
        /* harmony import */
        var _security_login_login_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./security/login/login.component */ 3035);
        /* harmony import */
        var _security_logout_logout_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./security/logout/logout.component */ 3531);
        /* harmony import */
        var _security_register_register_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./security/register/register.component */ 7697);
        /* harmony import */
        var _control_settings_settings_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./control/settings/settings.component */ 3475);
        /* harmony import */
        var _navigator_form_form_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./navigator/form/form.component */ 3135);
        /* harmony import */
        var _navigator_table_table_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./navigator/table/table.component */ 3530);
        /* harmony import */
        var _shared_likes_likes_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./shared/likes/likes.component */ 27);
        /* harmony import */
        var _shared_humanize_pipe__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./shared/humanize.pipe */ 5804);
        /* harmony import */
        var _shared_likes_likes_popup_likes_popup_component__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ./shared/likes/likes-popup/likes-popup.component */ 416);
        /* harmony import */
        var _control_user_user_component__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ./control/user/user.component */ 1355);
        /* harmony import */
        var _shared_visibility_visibility_component__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! ./shared/visibility/visibility.component */ 8049);
        /* harmony import */
        var _control_user_address_address_component__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! ./control/user/address/address.component */ 5510);
        /* harmony import */
        var _social_timeline_options_options_component__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! ./social/timeline/options/options.component */ 2127);
        /* harmony import */
        var _social_timeline_post_text_post_text_post_component__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! ./social/timeline/post/text-post/text-post.component */ 897);
        /* harmony import */
        var _social_timeline_post_image_post_image_post_component__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! ./social/timeline/post/image-post/image-post.component */ 632);
        /* harmony import */
        var _social_timeline_post_video_post_video_post_component__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! ./social/timeline/post/video-post/video-post.component */ 4749);
        /* harmony import */
        var _social_timeline_comments_comments_component__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! ./social/timeline/comments/comments.component */ 5384);
        /* harmony import */
        var _typed_template_directive__WEBPACK_IMPORTED_MODULE_22__ = __webpack_require__(/*! ./typed-template.directive */ 6538);
        /* harmony import */
        var _social_timeline_comments_comment_comment_component__WEBPACK_IMPORTED_MODULE_23__ = __webpack_require__(/*! ./social/timeline/comments/comment/comment.component */ 8917);
        /* harmony import */
        var _social_timeline_post_post_component__WEBPACK_IMPORTED_MODULE_24__ = __webpack_require__(/*! ./social/timeline/post/post.component */ 3609);
        /* harmony import */
        var _angular_common__WEBPACK_IMPORTED_MODULE_30__ = __webpack_require__(/*! @angular/common */ 4666);


        function appConfigFactory(service) {
            return () => {
                return service.init();
            };
        }

        class AppModule {
        }

        AppModule.ɵfac = function AppModule_Factory(t) {
            return new (t || AppModule)();
        };
        AppModule.ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_25__["ɵɵdefineNgModule"]({
            type: AppModule,
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_1__.AppComponent]
        });
        AppModule.ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_25__["ɵɵdefineInjector"]({
            providers: [{
                provide: _angular_router__WEBPACK_IMPORTED_MODULE_26__.RouteReuseStrategy,
                useClass: _app_routing_strategy_service__WEBPACK_IMPORTED_MODULE_2__.AppRoutingStrategyService
            }, {
                provide: _angular_core__WEBPACK_IMPORTED_MODULE_25__.APP_INITIALIZER,
                useFactory: appConfigFactory,
                deps: [_app_startup_service__WEBPACK_IMPORTED_MODULE_3__.AppStartupService],
                multi: true
            }],
            imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_27__.BrowserModule, _app_routing_module__WEBPACK_IMPORTED_MODULE_0__.AppRoutingModule, ng2_simplicity__WEBPACK_IMPORTED_MODULE_28__.Angular2SimplicityModule, _angular_forms__WEBPACK_IMPORTED_MODULE_29__.ReactiveFormsModule, _angular_forms__WEBPACK_IMPORTED_MODULE_29__.FormsModule]
        });
        (function () {
            (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_25__["ɵɵsetNgModuleScope"](AppModule, {
                declarations: [_typed_template_directive__WEBPACK_IMPORTED_MODULE_22__.TypedTemplateDirective, _app_component__WEBPACK_IMPORTED_MODULE_1__.AppComponent, _social_timeline_timeline_component__WEBPACK_IMPORTED_MODULE_4__.TimelineComponent, _security_login_login_component__WEBPACK_IMPORTED_MODULE_5__.LoginComponent, _security_logout_logout_component__WEBPACK_IMPORTED_MODULE_6__.LogoutComponent, _security_register_register_component__WEBPACK_IMPORTED_MODULE_7__.RegisterComponent, _control_settings_settings_component__WEBPACK_IMPORTED_MODULE_8__.SettingsComponent, _navigator_form_form_component__WEBPACK_IMPORTED_MODULE_9__.FormComponent, _navigator_table_table_component__WEBPACK_IMPORTED_MODULE_10__.TableComponent, _shared_likes_likes_component__WEBPACK_IMPORTED_MODULE_11__.LikesComponent, _shared_humanize_pipe__WEBPACK_IMPORTED_MODULE_12__.HumanizePipe, _shared_likes_likes_popup_likes_popup_component__WEBPACK_IMPORTED_MODULE_13__.LikesPopupComponent, _control_user_user_component__WEBPACK_IMPORTED_MODULE_14__.UserComponent, _shared_visibility_visibility_component__WEBPACK_IMPORTED_MODULE_15__.VisibilityComponent, _control_user_address_address_component__WEBPACK_IMPORTED_MODULE_16__.AddressComponent, _social_timeline_options_options_component__WEBPACK_IMPORTED_MODULE_17__.OptionsComponent, _social_timeline_post_text_post_text_post_component__WEBPACK_IMPORTED_MODULE_18__.TextPostComponent, _social_timeline_post_image_post_image_post_component__WEBPACK_IMPORTED_MODULE_19__.ImagePostComponent, _social_timeline_post_video_post_video_post_component__WEBPACK_IMPORTED_MODULE_20__.VideoPostComponent, _social_timeline_comments_comments_component__WEBPACK_IMPORTED_MODULE_21__.CommentsComponent, _social_timeline_comments_comment_comment_component__WEBPACK_IMPORTED_MODULE_23__.CommentComponent, _social_timeline_post_post_component__WEBPACK_IMPORTED_MODULE_24__.PostComponent],
                imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_27__.BrowserModule, _app_routing_module__WEBPACK_IMPORTED_MODULE_0__.AppRoutingModule, ng2_simplicity__WEBPACK_IMPORTED_MODULE_28__.Angular2SimplicityModule, _angular_forms__WEBPACK_IMPORTED_MODULE_29__.ReactiveFormsModule, _angular_forms__WEBPACK_IMPORTED_MODULE_29__.FormsModule]
            });
        })();
        _angular_core__WEBPACK_IMPORTED_MODULE_25__["ɵɵsetComponentScope"](_social_timeline_timeline_component__WEBPACK_IMPORTED_MODULE_4__.TimelineComponent, [ng2_simplicity__WEBPACK_IMPORTED_MODULE_28__.AsInputContainerComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_28__.AsInputDirective, ng2_simplicity__WEBPACK_IMPORTED_MODULE_28__.AsInfiniteScrollComponent, _angular_forms__WEBPACK_IMPORTED_MODULE_29__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_29__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_29__.NgModel, _typed_template_directive__WEBPACK_IMPORTED_MODULE_22__.TypedTemplateDirective, _social_timeline_post_post_component__WEBPACK_IMPORTED_MODULE_24__.PostComponent], []);
        _angular_core__WEBPACK_IMPORTED_MODULE_25__["ɵɵsetComponentScope"](_social_timeline_comments_comment_comment_component__WEBPACK_IMPORTED_MODULE_23__.CommentComponent, [_angular_common__WEBPACK_IMPORTED_MODULE_30__.NgIf, _shared_likes_likes_component__WEBPACK_IMPORTED_MODULE_11__.LikesComponent, _social_timeline_comments_comments_component__WEBPACK_IMPORTED_MODULE_21__.CommentsComponent], []);

        /***/
    }),

    /***/ 8946:
    /*!*********************************!*\
  !*** ./src/app/app.resolver.ts ***!
  \*********************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppResolver": () => (/* binding */ AppResolver)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);

        class AppResolver {
            resolve(route, state) {
                let urls = route.data["urls"];
                let regex = /\{(\w+)\}/g;
                let processed = [];
                for (const url of urls) {
                    let entries = Object.entries(route.params);
                    if (entries.length > 0) {
                        for (const [key, value] of entries) {
                            processed.push(url.value.replace(regex, (match, group) => {
                                let newVar = route.paramMap.get(group);
                                return newVar;
                            }));
                        }
                    } else {
                        processed.push(url.value);
                    }
                }
                let promises = [];
                for (const url of processed) {
                    promises.push(secureFetch(url).then(response => {
                        if (response.ok) {
                            return response.json();
                        }
                        throw new Error("");
                    }));
                }
                return Promise.all(promises);
            }
        }

        AppResolver.ɵfac = function AppResolver_Factory(t) {
            return new (t || AppResolver)();
        };
        AppResolver.ɵprov = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjectable"]({
            token: AppResolver,
            factory: AppResolver.ɵfac,
            providedIn: 'root'
        });

        /***/
    }),

    /***/ 3475:
    /*!********************************************************!*\
  !*** ./src/app/control/settings/settings.component.ts ***!
  \********************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "SettingsComponent": () => (/* binding */ SettingsComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _app_startup_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../app-startup.service */ 9609);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ 124);


        const _c0 = function (a0) {
            return {
                link: a0
            };
        };

        class SettingsComponent {
            constructor(window, startupService) {
                this.window = window;
                this.startupService = startupService;
            }

            get service() {
                return btoa("service");
            }

            onDocumentClick() {
                this.window.close();
            }
        }

        SettingsComponent.ɵfac = function SettingsComponent_Factory(t) {
            return new (t || SettingsComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__.AsWindowComponent), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_app_startup_service__WEBPACK_IMPORTED_MODULE_0__.AppStartupService));
        };
        SettingsComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
            type: SettingsComponent,
            selectors: [["app-settings"]],
            hostBindings: function SettingsComponent_HostBindings(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("click", function SettingsComponent_click_HostBindingHandler() {
                        return ctx.onDocumentClick();
                    }, false, _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵresolveDocument"]);
                }
            },
            decls: 16,
            vars: 5,
            consts: [["content", ""], [2, "margin", "10px"], [2, "display", "flex", "align-items", "center", "margin-bottom", "8px"], [2, "height", "64px", "width", "64px", "border-radius", "50%", 3, "src"], [2, "margin-left", "8px"], ["routerLink", "/navigator/form", 1, "left", 2, "margin-bottom", "8px", 3, "queryParams"], [2, "display", "flex", "align-items", "center"], [1, "material-icons", 2, "margin-right", "8px"], ["routerLink", "/security/logout", 1, "left", 2, "margin-bottom", "8px"]],
            template: function SettingsComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "div", 0)(1, "div", 1)(2, "div", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](3, "img", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](4, "div", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](6, "a", 5)(7, "div", 6)(8, "span", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](9, "assistant_direction");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](10, " Navigator ");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](11, "a", 8)(12, "div", 6)(13, "span", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](14, "logout");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](15, " Logout ");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("src", ctx.startupService.model.form.picture.url, _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵsanitizeUrl"]);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate"](ctx.startupService.model.form.nickName);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("queryParams", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpureFunction1"](3, _c0, ctx.service));
                }
            },
            dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterLink],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 5510:
    /*!***********************************************************!*\
  !*** ./src/app/control/user/address/address.component.ts ***!
  \***********************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AddressComponent": () => (/* binding */ AddressComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _app_startup_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../app-startup.service */ 9609);
        /* harmony import */
        var _angular_common__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common */ 4666);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ 2508);


        function AddressComponent_ng_template_3_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](0);
            }
            if (rf & 2) {
                const address_r2 = ctx.$implicit;
                _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate1"](" ", address_r2.name, " ");
            }
        }

        function AddressComponent_div_4_Template(rf, ctx) {
            if (rf & 1) {
                const _r4 = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "div")(1, "as-input-container", 5)(2, "input", 6);
                _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("ngModelChange", function AddressComponent_div_4_Template_input_ngModelChange_2_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵrestoreView"](_r4);
                    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵnextContext"]();
                    return _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵresetView"](ctx_r3.address.description = $event);
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()();
            }
            if (rf & 2) {
                const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("ngModel", ctx_r1.address.description);
            }
        }

        class AddressComponent {
            constructor(window, service) {
                this.window = window;
                this.service = service;
                this.submit = new _angular_core__WEBPACK_IMPORTED_MODULE_1__.EventEmitter();
            }

            onAddressLoad(event) {
                let url = (0, ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__.generateURL)("service/social/info/address/search");
                url.searchParams.append("value", event.query.value);
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows, response.size);
                });
            }

            onCategoriesLoad(event) {
                let link = "service/control/users/user/connections/connection/categories";
                const url = new URL(window.location.origin + "/" + link);
                url.searchParams.append("index", event.query.index + "");
                url.searchParams.append("limit", event.query.limit + "");
                url.searchParams.append("owner", this.service.model.form.id);
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows, response.size);
                });
            }

            onDocumentClick() {
                secureFetch("service/social/info/addresses/address", "POST", this.address).then(() => {
                    this.submit.emit(this.address);
                    this.window.close();
                });
            }
        }

        AddressComponent.ɵfac = function AddressComponent_Factory(t) {
            return new (t || AddressComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__.AsWindowComponent), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_app_startup_service__WEBPACK_IMPORTED_MODULE_0__.AppStartupService));
        };
        AddressComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
            type: AddressComponent,
            selectors: [["app-address"]],
            outputs: {
                submit: "submit"
            },
            decls: 7,
            vars: 2,
            consts: [["content", ""], ["placeholder", "Click here...", 2, "width", "100%"], ["trackBy", "name", "label", "name", 2, "width", "100%", 3, "ngModel", "ngModelChange", "items"], [4, "ngIf"], ["type", "button", 1, "normal", 3, "click"], ["placeholder", "Description"], ["type", "text", 3, "ngModel", "ngModelChange"]],
            template: function AddressComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "div", 0)(1, "as-input-container", 1)(2, "as-lazy-select", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("ngModelChange", function AddressComponent_Template_as_lazy_select_ngModelChange_2_listener($event) {
                        return ctx.address = $event;
                    })("items", function AddressComponent_Template_as_lazy_select_items_2_listener($event) {
                        return ctx.onAddressLoad($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtemplate"](3, AddressComponent_ng_template_3_Template, 1, 1, "ng-template");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtemplate"](4, AddressComponent_div_4_Template, 3, 1, "div", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](5, "button", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("click", function AddressComponent_Template_button_click_5_listener() {
                        return ctx.onDocumentClick();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](6, "Okay");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("ngModel", ctx.address);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("ngIf", ctx.address);
                }
            },
            dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_3__.NgIf, ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__.AsInputContainerComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__.AsInputDirective, ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__.AsLazySelectComponent, _angular_forms__WEBPACK_IMPORTED_MODULE_4__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_4__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_4__.NgModel],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 1355:
    /*!************************************************!*\
  !*** ./src/app/control/user/user.component.ts ***!
  \************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "UserComponent": () => (/* binding */ UserComponent)
            /* harmony export */
        });
        /* harmony import */
        var _app_classes__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../app.classes */ 8941);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _address_address_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./address/address.component */ 5510);
        /* harmony import */
        var mapbox_gl__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! mapbox-gl */ 2333);
        /* harmony import */
        var mapbox_gl__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(mapbox_gl__WEBPACK_IMPORTED_MODULE_2__);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var _angular_common__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/common */ 4666);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/forms */ 2508);
        /* harmony import */
        var _social_timeline_timeline_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../social/timeline/timeline.component */ 4639);


        function UserComponent_ng_template_17_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](0, "app-timeline", 9);
            }
            if (rf & 2) {
                const ctx_r0 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("owner", ctx_r0.user.form.id);
            }
        }

        function UserComponent_ng_template_19_div_3_Template(rf, ctx) {
            if (rf & 1) {
                const _r13 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 21)(1, "as-input-container", 22);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](2, "input", 23);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](3, "button", 24);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_div_3_Template_button_click_3_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r13);
                    const ctx_r12 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r12.onVisibilityColumnClick($event, "firstName", ctx_r12.user.form));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](4, "security");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
            }
        }

        function UserComponent_ng_template_19_div_4_Template(rf, ctx) {
            if (rf & 1) {
                const _r15 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 21)(1, "as-input-container", 25);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](2, "input", 26);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](3, "button", 24);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_div_4_Template_button_click_3_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r15);
                    const ctx_r14 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r14.onVisibilityColumnClick($event, "lastName", ctx_r14.user.form));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](4, "security");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
            }
        }

        function UserComponent_ng_template_19_div_5_Template(rf, ctx) {
            if (rf & 1) {
                const _r17 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 21)(1, "as-input-container", 27);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](2, "input", 28);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](3, "button", 24);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_div_5_Template_button_click_3_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r17);
                    const ctx_r16 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r16.onVisibilityColumnClick($event, "birthDate", ctx_r16.user.form));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](4, "security");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
            }
        }

        function UserComponent_ng_template_19_div_6_ng_template_3_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](0);
            }
            if (rf & 2) {
                const role_r19 = ctx.$implicit;
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate1"](" ", role_r19.name, " ");
            }
        }

        function UserComponent_ng_template_19_div_6_Template(rf, ctx) {
            if (rf & 1) {
                const _r21 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div")(1, "as-input-container", 29)(2, "as-lazy-select", 30);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("items", function UserComponent_ng_template_19_div_6_Template_as_lazy_select_items_2_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r21);
                    const ctx_r20 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r20.onRolesLoad($event));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](3, UserComponent_ng_template_19_div_6_ng_template_3_Template, 1, 1, "ng-template");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()();
            }
            if (rf & 2) {
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("multiSelect", true);
            }
        }

        function UserComponent_ng_template_19_div_7_div_2_Template(rf, ctx) {
            if (rf & 1) {
                const _r25 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 38)(1, "as-input-container", 39);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](2, "input", 40);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](3, "button", 35);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_div_7_div_2_Template_button_click_3_listener() {
                    const restoredCtx = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r25);
                    const email_r23 = restoredCtx.$implicit;
                    const ctx_r24 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](3);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r24.removeEmail(email_r23));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](4, "delete");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
            }
            if (rf & 2) {
                const email_r23 = ctx.$implicit;
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("formGroup", email_r23);
            }
        }

        function UserComponent_ng_template_19_div_7_Template(rf, ctx) {
            if (rf & 1) {
                const _r27 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 31)(1, "div", 32);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](2, UserComponent_ng_template_19_div_7_div_2_Template, 5, 1, "div", 33);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](3, "div", 34)(4, "button", 35);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_div_7_Template_button_click_4_listener() {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r27);
                    const ctx_r26 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r26.addEmail(ctx_r26.properties["emails"]));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](5, "add");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](6, "div", 36);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](7, "Add Email");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](8, "button", 37);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_div_7_Template_button_click_8_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r27);
                    const ctx_r28 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r28.onVisibilityColumnClick($event, "emails", ctx_r28.user.form));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](9, "security");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
            }
            if (rf & 2) {
                const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngForOf", ctx_r7.emails.controls);
            }
        }

        function UserComponent_ng_template_19_button_10_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "button", 41);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Submit");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
            }
        }

        function UserComponent_ng_template_19_ng_template_13_Template(rf, ctx) {
            if (rf & 1) {
                const _r31 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 42)(1, "address", 43)(2, "div", 16)(3, "div")(4, "strong");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](5);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](6, "br");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](7, "div");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](8);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](9, "div");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](10);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](11, "div");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](12);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](13, "div");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](14);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](15, "div", 44);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](16, "button", 45);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_ng_template_13_Template_button_click_16_listener() {
                    const restoredCtx = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r31);
                    const address_r29 = restoredCtx.$implicit;
                    const ctx_r30 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r30.removeAddress(address_r29));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](17, "delete");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](18, "button", 37);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_ng_template_13_Template_button_click_18_listener($event) {
                    const restoredCtx = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r31);
                    const address_r29 = restoredCtx.$implicit;
                    const ctx_r32 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r32.onVisibilityRowClick($event, address_r29));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](19, "security");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
            }
            if (rf & 2) {
                const address_r29 = ctx.$implicit;
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](5);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](address_r29.description);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](address_r29.street);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](address_r29.zipCode);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](address_r29.state);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](address_r29.country);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("id", address_r29.id);
            }
        }

        function UserComponent_ng_template_19_div_14_Template(rf, ctx) {
            if (rf & 1) {
                const _r34 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 34)(1, "button", 35);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function UserComponent_ng_template_19_div_14_Template_button_click_1_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r34);
                    const ctx_r33 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"](2);
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r33.addAddress($event));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](2, "add");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](3, "span", 36);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](4, "Add Address");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
            }
        }

        function UserComponent_ng_template_19_Template(rf, ctx) {
            if (rf & 1) {
                const _r36 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div")(1, "as-input-container", 10);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](2, "input", 11);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](3, UserComponent_ng_template_19_div_3_Template, 5, 0, "div", 12);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](4, UserComponent_ng_template_19_div_4_Template, 5, 0, "div", 12);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](5, UserComponent_ng_template_19_div_5_Template, 5, 0, "div", 12);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](6, UserComponent_ng_template_19_div_6_Template, 4, 1, "div", 13);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](7, UserComponent_ng_template_19_div_7_Template, 10, 1, "div", 14);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](8, "div", 15);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](9, "div", 16);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](10, UserComponent_ng_template_19_button_10_Template, 2, 0, "button", 17);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](11, "as-lazy-list", 18, 19);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("items", function UserComponent_ng_template_19_Template_as_lazy_list_items_11_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r36);
                    const ctx_r35 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
                    return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r35.onAddressesLoad($event));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](13, UserComponent_ng_template_19_ng_template_13_Template, 20, 6, "ng-template");
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](14, UserComponent_ng_template_19_div_14_Template, 5, 0, "div", 20);
            }
            if (rf & 2) {
                const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx_r1.properties["firstName"]);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx_r1.properties["lastName"]);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx_r1.properties["birthDate"]);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx_r1.properties["roles"]);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx_r1.properties["emails"]);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx_r1.user.$schema.links["update"]);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](4);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx_r1.addressSchema == null ? null : ctx_r1.addressSchema.links == null ? null : ctx_r1.addressSchema.links.create);
            }
        }

        function UserComponent_ng_template_21_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](0, " Life Events ");
            }
        }

        mapbox_gl__WEBPACK_IMPORTED_MODULE_2__.accessToken = "pk.eyJ1IjoiYW5qdW5hciIsImEiOiJjbDFuczBnc20wd2g4M2NvMm1yMWp4aHpiIn0.1KbDOpN0gPaRq5MzS-N0Zw";

        class UserComponent extends _app_classes__WEBPACK_IMPORTED_MODULE_0__.AppView {
            get properties() {
                let property = this.user.$schema.properties["form"];
                return property.properties;
            }

            get emails() {
                let control = this.form.controls["form"];
                return control.controls["emails"];
            }

            constructor(route, model2Schema, windowManager) {
                super(route, windowManager);
                this.model2Schema = model2Schema;
                this.page = 0;
                this.form = model2Schema.create(this.user.$schema.properties, this.user);
            }

            onSubmit() {
                let user = this.form.getValue();
                secureFetch("service/control/users/user", "PUT", user);
            }

            onRolesLoad(event) {
                let url = (0, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.generateURL)("service/control/roles");
                url.searchParams.append("index", event.query.index + "");
                url.searchParams.append("limit", event.query.limit + "");
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows, response.size);
                });
            }

            onAddressesLoad(event) {
                let url = (0, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.generateURL)(`service/social/info/addresses?owner=${this.user.form.id}`);
                url.searchParams.append("index", event.query.index + "");
                url.searchParams.append("limit", event.query.limit + "");
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows, response.size);
                    this.addressSchema = response.$schema;
                    if (response.rows) {
                        for (const row of response.rows) {
                            setTimeout(() => {
                                let map = new mapbox_gl__WEBPACK_IMPORTED_MODULE_2__.Map({
                                    container: row.id,
                                    style: 'mapbox://styles/mapbox/dark-v11',
                                    center: [row.x, row.y],
                                    zoom: 14
                                });
                                const marker = new mapbox_gl__WEBPACK_IMPORTED_MODULE_2__.Marker().setLngLat([row.x, row.y]).addTo(map);
                            });
                        }
                    }
                });
            }

            removeEmail(value) {
                let indexOf = this.emails.controls.indexOf(value);
                this.emails.removeAt(indexOf);
                this.form.markAsDirty();
            }

            addEmail(schema) {
                let form = this.model2Schema.schema2Form(schema.items, {});
                this.emails.push(form);
                this.form.markAsDirty();
            }

            addAddress(event) {
                event.stopPropagation();
                let windowRef = this.windowManager.create(_address_address_component__WEBPACK_IMPORTED_MODULE_1__.AddressComponent, {
                    header: "Address",
                    width: "400px"
                });
                windowRef.instance.submit.subscribe(() => {
                    this.addresses.load();
                });
            }

            removeAddress(value) {
                let url = (0, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.generateURL)("service/social/info/addresses/address");
                url.searchParams.append("id", value.id);
                secureFetch(url.toString(), "DELETE");
            }
        }

        UserComponent.ɵfac = function UserComponent_Factory(t) {
            return new (t || UserComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_6__.ActivatedRoute), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsMetaFormService), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.WindowManagerService));
        };
        UserComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdefineComponent"]({
            type: UserComponent,
            selectors: [["app-user"]],
            viewQuery: function UserComponent_Query(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵviewQuery"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsLazyListComponent, 5);
                }
                if (rf & 2) {
                    let _t;
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵloadQuery"]()) && (ctx.addresses = _t.first);
                }
            },
            features: [_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵInheritDefinitionFeature"]],
            decls: 22,
            vars: 5,
            consts: [[2, "max-width", "600px", "margin", "auto"], [3, "formGroup", "ngSubmit"], ["formGroupName", "form"], [2, "position", "relative"], ["formControlName", "background", 2, "max-width", "600px", "height", "200px", 3, "cropping"], [2, "height", "128px", "width", "128px", "bottom", "-32px", "left", "10px", "position", "absolute"], ["formControlName", "picture", 2, "width", "128px", "height", "128px", 3, "cropping"], [2, "margin-top", "64px", "margin-bottom", "10px", 3, "page", "pageChange"], [3, "page"], [3, "owner"], ["placeholder", "Nick Name"], ["type", "text", "formControlName", "nickName"], ["style", "display: flex; width: 100%;", 4, "ngIf"], [4, "ngIf"], ["style", "display: flex; width: 100%; align-items: flex-start", 4, "ngIf"], [2, "display", "flex"], [2, "flex", "1"], ["type", "submit", "class", "normal", 4, "ngIf"], [3, "items"], ["addresses", ""], ["style", "display: flex; align-items: center", 4, "ngIf"], [2, "display", "flex", "width", "100%"], ["placeholder", "First Name", 2, "flex", "1"], ["type", "text", "formControlName", "firstName"], [1, "material-icons", 3, "click"], ["placeholder", "Last Name", 2, "flex", "1"], ["type", "text", "formControlName", "lastName"], ["placeholder", "Birthdate", 2, "flex", "1"], ["type", "date", "formControlName", "birthDate"], ["placeholder", "Roles"], ["formControlName", "roles", 3, "multiSelect", "items"], [2, "display", "flex", "width", "100%", "align-items", "flex-start"], ["formArrayName", "emails", 2, "flex", "1"], ["style", "display: flex", 3, "formGroup", 4, "ngFor", "ngForOf"], [2, "display", "flex", "align-items", "center"], ["type", "button", 1, "material-icons", 3, "click"], [2, "font-size", "small"], [1, "material-icons", 2, "height", "40px", 3, "click"], [2, "display", "flex", 3, "formGroup"], ["placeholder", "Email", 2, "flex", "1"], ["type", "email", "formControlName", "value"], ["type", "submit", 1, "normal"], [2, "display", "flex", "align-items", "flex-start", "padding-top", "10px"], [2, "font-style", "normal", "font-size", "small", "flex", "1", "display", "flex"], [2, "width", "168px", "height", "168px", 3, "id"], ["type", "button", 1, "material-icons", 2, "height", "40px", "margin-left", "5px", 3, "click"]],
            template: function UserComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 0)(1, "div")(2, "form", 1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("ngSubmit", function UserComponent_Template_form_ngSubmit_2_listener() {
                        return ctx.onSubmit();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](3, "div", 2)(4, "div", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](5, "as-image-upload", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](6, "div", 5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](7, "as-image-upload", 6);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](8, "as-tabs", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("pageChange", function UserComponent_Template_as_tabs_pageChange_8_listener($event) {
                        return ctx.page = $event;
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](9, "as-tab");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](10, "Timeline");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](11, "as-tab");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](12, "Details");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](13, "as-tab");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](14, "Life Events");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](15, "as-pages", 8)(16, "as-page");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](17, UserComponent_ng_template_17_Template, 1, 1, "ng-template");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](18, "as-page");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](19, UserComponent_ng_template_19_Template, 15, 7, "ng-template");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](20, "as-page");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](21, UserComponent_ng_template_21_Template, 1, 0, "ng-template");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()()()()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("formGroup", ctx.form);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("cropping", true);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("cropping", true);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("page", ctx.page);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("page", ctx.page);
                }
            },
            dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_7__.NgForOf, _angular_common__WEBPACK_IMPORTED_MODULE_7__.NgIf, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsTabComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsTabsComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsPageComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsPagesComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsImageUploadComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsInputContainerComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsInputDirective, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsLazySelectComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.AsLazyListComponent, _angular_forms__WEBPACK_IMPORTED_MODULE_8__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_8__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_8__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_8__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_8__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_8__.FormControlName, _angular_forms__WEBPACK_IMPORTED_MODULE_8__.FormGroupName, _angular_forms__WEBPACK_IMPORTED_MODULE_8__.FormArrayName, _social_timeline_timeline_component__WEBPACK_IMPORTED_MODULE_3__.TimelineComponent],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 3854:
    /*!*****************************************************!*\
  !*** ./src/app/navigator/app-navigator.resolver.ts ***!
  \*****************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppNavigatorResolver": () => (/* binding */ AppNavigatorResolver)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);

        class AppNavigatorResolver {
            resolve(route, state) {
                let link = atob(route.queryParams["link"]);
                return secureFetch(link).then(response => response.json());
            }
        }

        AppNavigatorResolver.ɵfac = function AppNavigatorResolver_Factory(t) {
            return new (t || AppNavigatorResolver)();
        };
        AppNavigatorResolver.ɵprov = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjectable"]({
            token: AppNavigatorResolver,
            factory: AppNavigatorResolver.ɵfac,
            providedIn: 'root'
        });

        /***/
    }),

    /***/ 1341:
    /*!****************************************************!*\
  !*** ./src/app/navigator/app-navigator.service.ts ***!
  \****************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "AppNavigatorService": () => (/* binding */ AppNavigatorService)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);

        class AppNavigatorService {
            constructor() {
            }
        }

        AppNavigatorService.ɵfac = function AppNavigatorService_Factory(t) {
            return new (t || AppNavigatorService)();
        };
        AppNavigatorService.ɵprov = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjectable"]({
            token: AppNavigatorService,
            factory: AppNavigatorService.ɵfac,
            providedIn: 'root'
        });

        /***/
    }),

    /***/ 3135:
    /*!**************************************************!*\
  !*** ./src/app/navigator/form/form.component.ts ***!
  \**************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "FormComponent": () => (/* binding */ FormComponent)
            /* harmony export */
        });
        /* harmony import */
        var _shared_visibility_visibility_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../shared/visibility/visibility.component */ 8049);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var _app_navigator_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../app-navigator.service */ 1341);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ng2-simplicity */ 4942);


        function FormComponent_ng_template_2_Template(rf, ctx) {
            if (rf & 1) {
                const _r4 = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](0, "button", 1);
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵlistener"]("click", function FormComponent_ng_template_2_Template_button_click_0_listener($event) {
                    const restoredCtx = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵrestoreView"](_r4);
                    const node_r1 = restoredCtx.$implicit;
                    const model_r2 = restoredCtx.model;
                    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵnextContext"]();
                    return _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵresetView"](ctx_r3.onVisibilityColumnClick($event, node_r1.key, model_r2));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](1, "security");
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
            }
        }

        class FormComponent {
            constructor(router, route, service, windowManager) {
                this.router = router;
                this.route = route;
                this.service = service;
                this.windowManager = windowManager;
                let data = route.data;
                this.model = data["value"].model;
                service.links = Object.entries(this.model.$schema.links).filter(([key, value]) => {
                    let object = value;
                    return object.method === "GET";
                }).reduce((prev, [key, value]) => {
                    prev[key] = value;
                    return prev;
                }, {});
                route.queryParams.subscribe(params => {
                    this.header = atob(params["link"]);
                });
            }

            onVisibilityColumnClick(event, property, model) {
                event.stopPropagation();
                let url = `service/shared/visibility/column?property=${property}&class=${model["@type"]}&id=${model.id}`;
                secureFetch(url).then(response => response.json()).then(response => {
                    let windowRef = this.windowManager.create(_shared_visibility_visibility_component__WEBPACK_IMPORTED_MODULE_0__.VisibilityComponent, {
                        header: "Visibility",
                        width: "300px"
                    });
                    windowRef.instance.property = property;
                    windowRef.instance.clazz = model["@type"];
                    windowRef.instance.id = model.id;
                    windowRef.instance.model = response;
                });
            }

            onSubmit(event) {
                let method = event.link.value.method;
                secureFetch(event.link.value.url, method, event.model.form).then(response => response.json()).then(response => {
                    if (response.$schema.links.redirect) {
                        let link = response.$schema.links.redirect;
                        this.router.navigate(["navigator/" + link.type], {
                            queryParams: {
                                link: btoa(link.url)
                            }
                        });
                    }
                });
            }
        }

        FormComponent.ɵfac = function FormComponent_Factory(t) {
            return new (t || FormComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_3__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_3__.ActivatedRoute), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_app_navigator_service__WEBPACK_IMPORTED_MODULE_1__.AppNavigatorService), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.WindowManagerService));
        };
        FormComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineComponent"]({
            type: FormComponent,
            selectors: [["app-form"]],
            decls: 3,
            vars: 2,
            consts: [[3, "model", "submit"], ["type", "button", 1, "material-icons", 3, "click"]],
            template: function FormComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](0);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](1, "as-meta-form", 0);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵlistener"]("submit", function FormComponent_Template_as_meta_form_submit_1_listener($event) {
                        return ctx.onSubmit($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtemplate"](2, FormComponent_ng_template_2_Template, 2, 0, "ng-template");
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtextInterpolate1"]("", ctx.header, "\n");
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵproperty"]("model", ctx.model);
                }
            },
            dependencies: [ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsMetaFormComponent],
            styles: ["app-form {\r\n  display: block;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvbmF2aWdhdG9yL2Zvcm0vZm9ybS5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsY0FBYztBQUNoQiIsInNvdXJjZXNDb250ZW50IjpbImFwcC1mb3JtIHtcclxuICBkaXNwbGF5OiBibG9jaztcclxufVxyXG4iXSwic291cmNlUm9vdCI6IiJ9 */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 3530:
    /*!****************************************************!*\
  !*** ./src/app/navigator/table/table.component.ts ***!
  \****************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "TableComponent": () => (/* binding */ TableComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var _app_navigator_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../app-navigator.service */ 1341);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ng2-simplicity */ 4942);


        class TableComponent {
            constructor(router, route, service) {
                this.router = router;
                this.route = route;
                this.service = service;
                route.queryParams.subscribe(params => {
                    this.header = atob(params["link"]);
                });
            }

            ngOnInit() {
                let queryParams = this.route.queryParams;
                let queryParam = queryParams.value["link"];
                this.link = atob(queryParam);
            }

            loader(event) {
                const url = new URL(window.location.origin + "/" + this.link);
                url.searchParams.append("index", event.query.index + "");
                url.searchParams.append("limit", event.query.limit + "");
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows, response.size, response.$schema);
                });
            }

            onRowClick(model) {
                this.router.navigate(["/navigator/form"], {
                    queryParams: {
                        link: btoa(model.links.read.url)
                    }
                });
            }

            onLoad(model) {
                this.service.links = model.$schema.links;
            }
        }

        TableComponent.ɵfac = function TableComponent_Factory(t) {
            return new (t || TableComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.ActivatedRoute), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_app_navigator_service__WEBPACK_IMPORTED_MODULE_0__.AppNavigatorService));
        };
        TableComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
            type: TableComponent,
            selectors: [["app-table"]],
            decls: 2,
            vars: 1,
            consts: [[3, "items", "rowClick", "load"]],
            template: function TableComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](0);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](1, "as-meta-table", 0);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("items", function TableComponent_Template_as_meta_table_items_1_listener($event) {
                        return ctx.loader($event);
                    })("rowClick", function TableComponent_Template_as_meta_table_rowClick_1_listener($event) {
                        return ctx.onRowClick($event);
                    })("load", function TableComponent_Template_as_meta_table_load_1_listener($event) {
                        return ctx.onLoad($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate1"]("", ctx.header, "\n");
                }
            },
            dependencies: [ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsMetaTableComponent],
            styles: ["app-table[_ngcontent-%COMP%] {\r\n  display: block;\r\n}\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvbmF2aWdhdG9yL3RhYmxlL3RhYmxlLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxjQUFjO0FBQ2hCIiwic291cmNlc0NvbnRlbnQiOlsiYXBwLXRhYmxlIHtcclxuICBkaXNwbGF5OiBibG9jaztcclxufVxyXG4iXSwic291cmNlUm9vdCI6IiJ9 */"]
        });

        /***/
    }),

    /***/ 2235:
    /*!*********************************!*\
  !*** ./src/app/rest.classes.ts ***!
  \*********************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* tslint:disable */
        /* eslint-disable */
// Generated using typescript-generator version 3.1.1185 on 2023-03-11 20:50:02.


        /***/
    }),

    /***/ 3035:
    /*!***************************************************!*\
  !*** ./src/app/security/login/login.component.ts ***!
  \***************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "LoginComponent": () => (/* binding */ LoginComponent)
            /* harmony export */
        });
        /* harmony import */
        var _app_classes__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../app.classes */ 8941);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _app_startup_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../app-startup.service */ 9609);
        /* harmony import */
        var _angular_common__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/common */ 4666);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ 2508);


        function LoginComponent_div_12_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](0, "div", 13);
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](1, "Invalid User or Password");
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
            }
        }

        class LoginComponent extends _app_classes__WEBPACK_IMPORTED_MODULE_0__.AppView {
            constructor(router, route, service, startUp, windowManager) {
                super(route, windowManager);
                this.router = router;
                this.service = service;
                this.startUp = startUp;
                this.form = service.create(this.model.$schema.properties, this.model);
            }

            onSubmit() {
                let body = this.form.getValue();
                secureFetch("service/security/login", "POST", body.form).then(response => {
                    this.startUp.init().then(() => {
                        this.router.navigate(["/timeline"]);
                    });
                }).catch(response => {
                    this.form.setErrors({
                        invalid: true
                    });
                    return response;
                });
            }
        }

        LoginComponent.ɵfac = function LoginComponent_Factory(t) {
            return new (t || LoginComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_3__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_3__.ActivatedRoute), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsMetaFormService), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_app_startup_service__WEBPACK_IMPORTED_MODULE_1__.AppStartupService), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.WindowManagerService));
        };
        LoginComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineComponent"]({
            type: LoginComponent,
            selectors: [["app-login"]],
            features: [_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵInheritDefinitionFeature"]],
            decls: 18,
            vars: 2,
            consts: [[2, "display", "flex", "align-content", "center", "justify-content", "center", "flex-wrap", "wrap", "height", "100%"], [2, "height", "fit-content"], [3, "formGroup", "ngSubmit"], ["formGroupName", "form", 2, "margin-top", "10px"], ["placeholder", "Email"], ["type", "text", "name", "email", "formControlName", "email", 2, "width", "250px"], ["placeholder", "Password"], ["type", "password", "name", "current-password", "formControlName", "password", 2, "width", "250px"], [2, "display", "flex", "align-content", "baseline"], ["style", "font-size: small; line-height: 26px; vertical-align: baseline; color: var(--main-error-color)", 4, "ngIf"], [2, "flex", "1"], ["type", "submit", 1, "normal", 2, "margin-right", "5px"], ["routerLink", "/security/register", 2, "font-size", "small", "line-height", "26px", "vertical-align", "baseline"], [2, "font-size", "small", "line-height", "26px", "vertical-align", "baseline", "color", "var(--main-error-color)"]],
            template: function LoginComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](0, "div", 0)(1, "div", 1)(2, "h1");
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](3, " Login ");
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelement"](4, "hr");
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](5, "form", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵlistener"]("ngSubmit", function LoginComponent_Template_form_ngSubmit_5_listener() {
                        return ctx.onSubmit();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](6, "div", 3)(7, "as-input-container", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelement"](8, "input", 5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](9, "as-input-container", 6);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelement"](10, "input", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](11, "div", 8);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtemplate"](12, LoginComponent_div_12_Template, 2, 0, "div", 9);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelement"](13, "div", 10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](14, "button", 11);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](15, "Login");
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](16, "a", 12);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](17, "Register");
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]()()()()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵadvance"](5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵproperty"]("formGroup", ctx.form);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵadvance"](7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵproperty"]("ngIf", ctx.form.errors == null ? null : ctx.form.errors["invalid"]);
                }
            },
            dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_5__.NgIf, _angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterLink, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsInputContainerComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsInputDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_6__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControlName, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupName],
            styles: ["app-login {\r\n  display: block;\r\n  height: 100%;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvc2VjdXJpdHkvbG9naW4vbG9naW4uY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLGNBQWM7RUFDZCxZQUFZO0FBQ2QiLCJzb3VyY2VzQ29udGVudCI6WyJhcHAtbG9naW4ge1xyXG4gIGRpc3BsYXk6IGJsb2NrO1xyXG4gIGhlaWdodDogMTAwJTtcclxufVxyXG4iXSwic291cmNlUm9vdCI6IiJ9 */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 3531:
    /*!*****************************************************!*\
  !*** ./src/app/security/logout/logout.component.ts ***!
  \*****************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "LogoutComponent": () => (/* binding */ LogoutComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var _app_startup_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../app-startup.service */ 9609);


        class LogoutComponent {
            constructor(router, startUp) {
                this.router = router;
                this.startUp = startUp;
            }

            onLogout() {
                secureFetch("service/security/logout", "POST").then(response => response.json()).then(response => {
                    this.startUp.init().then(() => {
                        this.router.navigate(["/security/login"]);
                    });
                });
            }
        }

        LogoutComponent.ɵfac = function LogoutComponent_Factory(t) {
            return new (t || LogoutComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_app_startup_service__WEBPACK_IMPORTED_MODULE_0__.AppStartupService));
        };
        LogoutComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
            type: LogoutComponent,
            selectors: [["app-logout"]],
            decls: 7,
            vars: 0,
            consts: [[2, "display", "flex", "align-content", "center", "justify-content", "center", "flex-wrap", "wrap", "height", "100%"], [2, "height", "fit-content"], ["type", "button", 1, "normal", 2, "margin-top", "10px", 3, "click"]],
            template: function LogoutComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "div", 0)(1, "div", 1)(2, "h3");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](3, "Are you sure you want to Logout?");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](4, "hr");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](5, "button", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("click", function LogoutComponent_Template_button_click_5_listener() {
                        return ctx.onLogout();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](6, "Logout");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()();
                }
            },
            styles: ["app-logout {\r\n  display: block;\r\n  height: 100%;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvc2VjdXJpdHkvbG9nb3V0L2xvZ291dC5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsY0FBYztFQUNkLFlBQVk7QUFDZCIsInNvdXJjZXNDb250ZW50IjpbImFwcC1sb2dvdXQge1xyXG4gIGRpc3BsYXk6IGJsb2NrO1xyXG4gIGhlaWdodDogMTAwJTtcclxufVxyXG4iXSwic291cmNlUm9vdCI6IiJ9 */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 7697:
    /*!*********************************************************!*\
  !*** ./src/app/security/register/register.component.ts ***!
  \*********************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "RegisterComponent": () => (/* binding */ RegisterComponent)
            /* harmony export */
        });
        /* harmony import */
        var _app_classes__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../app.classes */ 8941);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ 124);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ 2508);


        class RegisterComponent extends _app_classes__WEBPACK_IMPORTED_MODULE_0__.AppView {
            constructor(router, route, service, windowManager) {
                super(route, windowManager);
                this.router = router;
                this.service = service;
                this.form = service.create(this.model.$schema.properties, this.model);
            }

            onSubmit() {
                let body = this.form.getValue();
                secureFetch("service/security/register", "POST", body).then(response => response.json()).then(response => {
                    this.router.navigate(["/timeline"]);
                });
            }
        }

        RegisterComponent.ɵfac = function RegisterComponent_Factory(t) {
            return new (t || RegisterComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.ActivatedRoute), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsMetaFormService), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.WindowManagerService));
        };
        RegisterComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
            type: RegisterComponent,
            selectors: [["app-register"]],
            features: [_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵInheritDefinitionFeature"]],
            decls: 20,
            vars: 1,
            consts: [[2, "display", "flex", "align-content", "center", "justify-content", "center", "flex-wrap", "wrap", "height", "100%"], [2, "height", "fit-content"], [3, "formGroup", "ngSubmit"], ["placeholder", "Nick Name", 2, "margin-top", "10px"], ["type", "text", "name", "nickname", "formControlName", "nickname"], ["placeholder", "Email"], ["type", "text", "name", "email", "formControlName", "email"], ["placeholder", "Password"], ["type", "password", "name", "password", "formControlName", "password", 2, "width", "250px"], ["placeholder", "Confirm"], ["type", "password", "name", "confirm", "formControlName", "password", 2, "width", "250px"], [2, "display", "flex", "align-content", "baseline"], [2, "flex", "1"], ["type", "submit", 1, "normal", 2, "margin-right", "5px"], ["routerLink", "/security/login", 2, "font-size", "small", "line-height", "24px", "vertical-align", "baseline"]],
            template: function RegisterComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "div", 0)(1, "div", 1)(2, "h1");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](3, " Register ");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](4, "hr");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](5, "form", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("ngSubmit", function RegisterComponent_Template_form_ngSubmit_5_listener() {
                        return ctx.onSubmit();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](6, "as-input-container", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](7, "input", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](8, "as-input-container", 5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](9, "input", 6);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](10, "as-input-container", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](11, "input", 8);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](12, "as-input-container", 9);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](13, "input", 10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](14, "div", 11);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](15, "div", 12);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](16, "button", 13);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](17, "Register");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](18, "a", 14);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](19, "Login");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()()()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("formGroup", ctx.form);
                }
            },
            dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_2__.RouterLink, ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsInputContainerComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsInputDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_4__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_4__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_4__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_4__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_4__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_4__.FormControlName],
            styles: ["app-register {\r\n  display: block;\r\n  height: 100%;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvc2VjdXJpdHkvcmVnaXN0ZXIvcmVnaXN0ZXIuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLGNBQWM7RUFDZCxZQUFZO0FBQ2QiLCJzb3VyY2VzQ29udGVudCI6WyJhcHAtcmVnaXN0ZXIge1xyXG4gIGRpc3BsYXk6IGJsb2NrO1xyXG4gIGhlaWdodDogMTAwJTtcclxufVxyXG4iXSwic291cmNlUm9vdCI6IiJ9 */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 5804:
    /*!*****************************************!*\
  !*** ./src/app/shared/humanize.pipe.ts ***!
  \*****************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "HumanizePipe": () => (/* binding */ HumanizePipe)
            /* harmony export */
        });
        /* harmony import */
        var moment_moment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! moment/moment */ 6908);
        /* harmony import */
        var moment_moment__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(moment_moment__WEBPACK_IMPORTED_MODULE_0__);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);


        class HumanizePipe {
            transform(value, ...args) {
                let then = moment_moment__WEBPACK_IMPORTED_MODULE_0__(value);
                return then.fromNow();
            }
        }

        HumanizePipe.ɵfac = function HumanizePipe_Factory(t) {
            return new (t || HumanizePipe)();
        };
        HumanizePipe.ɵpipe = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefinePipe"]({
            name: "humanize",
            type: HumanizePipe,
            pure: true
        });

        /***/
    }),

    /***/ 416:
    /*!*******************************************************************!*\
  !*** ./src/app/shared/likes/likes-popup/likes-popup.component.ts ***!
  \*******************************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "LikesPopupComponent": () => (/* binding */ LikesPopupComponent)
            /* harmony export */
        });
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ 124);


        function LikesPopupComponent_ng_template_4_Template(rf, ctx) {
            if (rf & 1) {
                const _r4 = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 4)(1, "div", 5);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](2, "img", 6);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](3, "div", 7)(4, "a", 8);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function LikesPopupComponent_ng_template_4_Template_a_click_4_listener() {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵrestoreView"](_r4);
                    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵnextContext"]();
                    return _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵresetView"](ctx_r3.onUserClick());
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](5);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()()();
            }
            if (rf & 2) {
                const user_r2 = ctx.$implicit;
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("src", user_r2.picture.url, _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵsanitizeUrl"]);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](2);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("routerLink", "/" + user_r2.nickName);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate"](user_r2.name);
            }
        }

        class LikesPopupComponent {
            constructor(dialog) {
                this.dialog = dialog;
            }

            usersLoader(event) {
                let link = "service/shared/likes";
                const url = new URL(window.location.origin + "/" + link);
                url.searchParams.append("post", this.postId);
                url.searchParams.append("index", event.query.index + "");
                url.searchParams.append("limit", event.query.limit + "");
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows || []);
                });
            }

            onUserClick() {
                this.dialog.close();
            }

            ngAfterViewInit() {
                setTimeout(() => {
                    this.scrollArea.checkScrollBars();
                });
            }
        }

        LikesPopupComponent.ɵfac = function LikesPopupComponent_Factory(t) {
            return new (t || LikesPopupComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsDialogComponent));
        };
        LikesPopupComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({
            type: LikesPopupComponent,
            selectors: [["app-likes-popup"]],
            viewQuery: function LikesPopupComponent_Query(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵviewQuery"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsScrollAreaComponent, 5);
                }
                if (rf & 2) {
                    let _t;
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵloadQuery"]()) && (ctx.scrollArea = _t.first);
                }
            },
            decls: 5,
            vars: 0,
            consts: [["content", "", 2, "height", "100%"], [2, "height", "100%", "width", "100%"], ["scrollArea", ""], [3, "items"], [2, "display", "flex", "align-items", "center", "margin", "5px"], [2, "width", "40px", "height", "40px", "border-radius", "50%", "overflow", "hidden"], [2, "max-width", "40px", "max-height", "40px", 3, "src"], [2, "margin-left", "5px"], [3, "routerLink", "click"]],
            template: function LikesPopupComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0)(1, "as-scroll-area", 1, 2)(3, "as-infinity-scroll", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("items", function LikesPopupComponent_Template_as_infinity_scroll_items_3_listener($event) {
                        return ctx.usersLoader($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtemplate"](4, LikesPopupComponent_ng_template_4_Template, 6, 3, "ng-template");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()()();
                }
            },
            dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_2__.RouterLink, ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsScrollAreaComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsInfiniteScrollComponent],
            styles: ["app-likes-popup {\r\n  display: block;\r\n}\r\n\r\napp-likes-popup a:hover {\r\n  text-decoration: underline;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvc2hhcmVkL2xpa2VzL2xpa2VzLXBvcHVwL2xpa2VzLXBvcHVwLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxjQUFjO0FBQ2hCOztBQUVBO0VBQ0UsMEJBQTBCO0FBQzVCIiwic291cmNlc0NvbnRlbnQiOlsiYXBwLWxpa2VzLXBvcHVwIHtcclxuICBkaXNwbGF5OiBibG9jaztcclxufVxyXG5cclxuYXBwLWxpa2VzLXBvcHVwIGE6aG92ZXIge1xyXG4gIHRleHQtZGVjb3JhdGlvbjogdW5kZXJsaW5lO1xyXG59XHJcbiJdLCJzb3VyY2VSb290IjoiIn0= */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 27:
    /*!*************************************************!*\
  !*** ./src/app/shared/likes/likes.component.ts ***!
  \*************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "LikesComponent": () => (/* binding */ LikesComponent)
            /* harmony export */
        });
        /* harmony import */
        var _likes_popup_likes_popup_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./likes-popup/likes-popup.component */ 416);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ng2-simplicity */ 4942);


        class LikesComponent {
            constructor(windowManager) {
                this.windowManager = windowManager;
            }

            onShowUsers(event) {
                event.stopPropagation();
                let options = {
                    dialog: true,
                    width: "320px",
                    height: "200px",
                    header: "People who liked..."
                };
                let windowRef = this.windowManager.create(_likes_popup_likes_popup_component__WEBPACK_IMPORTED_MODULE_0__.LikesPopupComponent, options);
                windowRef.instance.postId = this.model.id;
            }
        }

        LikesComponent.ɵfac = function LikesComponent_Factory(t) {
            return new (t || LikesComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_2__.WindowManagerService));
        };
        LikesComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
            type: LikesComponent,
            selectors: [["app-likes"]],
            inputs: {
                model: "model"
            },
            decls: 6,
            vars: 3,
            consts: [[1, "container", 2, "cursor", "pointer", 3, "click"], [2, "display", "flex", "align-items", "center"], [1, "material-icons", 2, "vertical-align", "-5px"], [2, "font-size", "small", "margin-left", "5px"]],
            template: function LikesComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "a", 0);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("click", function LikesComponent_Template_a_click_0_listener($event) {
                        return ctx.onShowUsers($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](1, "div", 1)(2, "span", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](3, "thumb_up");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](4, "span", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵstyleProp"]("color", ctx.model.likes.active ? "var(--main-selected-color)" : "var(--main-font-color)");
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate"](ctx.model.likes.count);
                }
            },
            styles: ["app-likes {\r\n  display: block;\r\n}\r\n\r\napp-likes a.container:hover {\r\n  text-decoration: underline;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvc2hhcmVkL2xpa2VzL2xpa2VzLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxjQUFjO0FBQ2hCOztBQUVBO0VBQ0UsMEJBQTBCO0FBQzVCIiwic291cmNlc0NvbnRlbnQiOlsiYXBwLWxpa2VzIHtcclxuICBkaXNwbGF5OiBibG9jaztcclxufVxyXG5cclxuYXBwLWxpa2VzIGEuY29udGFpbmVyOmhvdmVyIHtcclxuICB0ZXh0LWRlY29yYXRpb246IHVuZGVybGluZTtcclxufVxyXG4iXSwic291cmNlUm9vdCI6IiJ9 */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 8049:
    /*!***********************************************************!*\
  !*** ./src/app/shared/visibility/visibility.component.ts ***!
  \***********************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "VisibilityComponent": () => (/* binding */ VisibilityComponent)
            /* harmony export */
        });
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ 2508);


        function VisibilityComponent_ng_template_3_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div");
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
            }
            if (rf & 2) {
                const category_r1 = ctx.$implicit;
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate"](category_r1.name);
            }
        }

        class VisibilityComponent {
            constructor(window) {
                this.window = window;
            }

            onLoad(event) {
                let link = "service/control/users/user/connections/connection/categories";
                const url = (0, ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.generateURL)(link);
                url.searchParams.append("index", event.query.index + "");
                url.searchParams.append("limit", event.query.limit + "");
                url.searchParams.append("owner", this.id);
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows, response.size);
                });
            }

            onChange(model) {
                let url;
                if (this.property) {
                    url = `service/shared/visibility/column?property=${this.property}&class=${this.clazz}&id=${this.id}`;
                } else {
                    url = `service/shared/visibility/row?class=${this.clazz}&id=${this.id}`;
                }
                secureFetch(url, "POST", model);
            }

            onWindowClick(event) {
                event.stopPropagation();
            }

            onDocumentClick() {
                this.window.close();
            }
        }

        VisibilityComponent.ɵfac = function VisibilityComponent_Factory(t) {
            return new (t || VisibilityComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsWindowComponent));
        };
        VisibilityComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({
            type: VisibilityComponent,
            selectors: [["app-visibility"]],
            hostBindings: function VisibilityComponent_HostBindings(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function VisibilityComponent_click_HostBindingHandler($event) {
                        return ctx.onWindowClick($event);
                    })("click", function VisibilityComponent_click_HostBindingHandler() {
                        return ctx.onDocumentClick();
                    }, false, _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵresolveDocument"]);
                }
            },
            decls: 4,
            vars: 2,
            consts: [["content", ""], ["placeholder", "Visibility", 2, "width", "100%"], [2, "width", "100%", 3, "ngModel", "multiSelect", "items", "ngModelChange"]],
            template: function VisibilityComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0)(1, "as-input-container", 1)(2, "as-lazy-select", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("items", function VisibilityComponent_Template_as_lazy_select_items_2_listener($event) {
                        return ctx.onLoad($event);
                    })("ngModelChange", function VisibilityComponent_Template_as_lazy_select_ngModelChange_2_listener($event) {
                        return ctx.model = $event;
                    })("ngModelChange", function VisibilityComponent_Template_as_lazy_select_ngModelChange_2_listener($event) {
                        return ctx.onChange($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtemplate"](3, VisibilityComponent_ng_template_3_Template, 2, 1, "ng-template");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("ngModel", ctx.model)("multiSelect", true);
                }
            },
            dependencies: [ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsInputContainerComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsLazySelectComponent, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgModel],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 8917:
    /*!***********************************************************************!*\
  !*** ./src/app/social/timeline/comments/comment/comment.component.ts ***!
  \***********************************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "CommentComponent": () => (/* binding */ CommentComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);

        function CommentComponent_app_comments_22_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](0, "app-comments", 14);
            }
            if (rf & 2) {
                const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("comment", ctx_r1.model);
            }
        }

        class CommentComponent {
            constructor() {
                this.reply = false;
            }

            onLike(comment, likes) {
                comment.likes.active = !comment.likes.active;
                secureFetch(`service/home/timeline/post/comments/comment?id=${comment.id}`, "PUT", comment).then(() => {
                    if (comment.likes.active) {
                        likes.model.likes.count++;
                    } else {
                        likes.model.likes.count--;
                    }
                });
            }
        }

        CommentComponent.ɵfac = function CommentComponent_Factory(t) {
            return new (t || CommentComponent)();
        };
        CommentComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({
            type: CommentComponent,
            selectors: [["app-comment"]],
            inputs: {
                model: "model",
                create: "create"
            },
            decls: 23,
            vars: 5,
            consts: [[2, "display", "flex", "width", "calc(100% - 16px)", "padding", "8px"], [2, "width", "48px", "height", "48px", "border-radius", "50%", 3, "src"], [2, "flex", "1", "padding", "5px"], [1, "options"], [2, "color", "var(--main-grey-color)"], [2, "display", "flex", "margin-top", "5px"], [2, "flex", "1", "display", "flex"], ["type", "button", 1, "material-icons", "options"], [3, "model"], ["likes", ""], [1, "normal", 3, "click"], [1, "material-icons", 2, "font-size", "small", "vertical-align", "-2px", "margin-right", "5px"], [1, "normal", 2, "margin-left", "5px", 3, "click"], [3, "comment", 4, "ngIf"], [3, "comment"]],
            template: function CommentComponent_Template(rf, ctx) {
                if (rf & 1) {
                    const _r2 = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵgetCurrentView"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](1, "img", 1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](2, "div", 2)(3, "div", 3)(4, "strong", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](6, "br");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](7, "div", 5)(8, "div", 6)(9, "div");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](11, "button", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](12, "more_vert");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](13, "app-likes", 8, 9);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](15, "br");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](16, "button", 10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function CommentComponent_Template_button_click_16_listener() {
                        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵrestoreView"](_r2);
                        const _r0 = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵreference"](14);
                        return _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵresetView"](ctx.onLike(ctx.model, _r0));
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](17, "span", 11);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](18, "thumb_up");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](19, "I Like ");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](20, "button", 12);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function CommentComponent_Template_button_click_20_listener() {
                        return ctx.reply = !ctx.reply;
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](21, "Reply");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtemplate"](22, CommentComponent_app_comments_22_Template, 1, 1, "app-comments", 13);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("src", ctx.model.owner.picture.url, _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵsanitizeUrl"]);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate1"](" ", ctx.model.owner.name, " ");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate"](ctx.model.text);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("model", ctx.model);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](9);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("ngIf", ctx.reply || !ctx.model.empty);
                }
            },
            styles: ["app-comment {\r\n  display: block;\r\n}\r\n\r\napp-comment div.options > div > div > button.options {\r\n  display: none;\r\n}\r\n\r\napp-comment div.options:hover  > div > div > button.options {\r\n  display: inline;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvc29jaWFsL3RpbWVsaW5lL2NvbW1lbnRzL2NvbW1lbnQvY29tbWVudC5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsY0FBYztBQUNoQjs7QUFFQTtFQUNFLGFBQWE7QUFDZjs7QUFFQTtFQUNFLGVBQWU7QUFDakIiLCJzb3VyY2VzQ29udGVudCI6WyJhcHAtY29tbWVudCB7XHJcbiAgZGlzcGxheTogYmxvY2s7XHJcbn1cclxuXHJcbmFwcC1jb21tZW50IGRpdi5vcHRpb25zID4gZGl2ID4gZGl2ID4gYnV0dG9uLm9wdGlvbnMge1xyXG4gIGRpc3BsYXk6IG5vbmU7XHJcbn1cclxuXHJcbmFwcC1jb21tZW50IGRpdi5vcHRpb25zOmhvdmVyICA+IGRpdiA+IGRpdiA+IGJ1dHRvbi5vcHRpb25zIHtcclxuICBkaXNwbGF5OiBpbmxpbmU7XHJcbn1cclxuIl0sInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 5384:
    /*!****************************************************************!*\
  !*** ./src/app/social/timeline/comments/comments.component.ts ***!
  \****************************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "CommentsComponent": () => (/* binding */ CommentsComponent)
            /* harmony export */
        });
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _angular_common__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common */ 4666);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ 2508);
        /* harmony import */
        var _typed_template_directive__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../typed-template.directive */ 6538);
        /* harmony import */
        var _comment_comment_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./comment/comment.component */ 8917);


        function CommentsComponent_ng_template_3_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelement"](0, "app-comment", 4);
            }
            if (rf & 2) {
                const comment_r2 = ctx.$implicit;
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵproperty"]("model", comment_r2);
            }
        }

        function CommentsComponent_form_4_Template(rf, ctx) {
            if (rf & 1) {
                const _r4 = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵgetCurrentView"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](0, "form", 5)(1, "fieldset", 6)(2, "textarea", 7);
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵlistener"]("keyup", function CommentsComponent_form_4_Template_textarea_keyup_2_listener($event) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵrestoreView"](_r4);
                    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵnextContext"]();
                    return _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵresetView"](ctx_r3.onKeyUp($event));
                });
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]()()();
            }
            if (rf & 2) {
                const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵproperty"]("formGroup", ctx_r1.formGroup);
            }
        }

        class CommentsComponent {
            constructor(service) {
                this.service = service;
            }

            ngOnInit() {
                let url = (0, ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.generateURL)("service/home/timeline/post/comments/comment/create");
                if (this.post) {
                    url.searchParams.append("post", this.post.id);
                }
                if (this.comment) {
                    url.searchParams.append("parent", this.comment.id);
                }
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    this.formGroup = this.service.create(response.$schema.properties, response);
                });
            }

            onLoad(event) {
                let url = (0, ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.generateURL)("service/home/timeline/post/comments");
                url.searchParams.append("index", event.query.index + "");
                url.searchParams.append("limit", event.query.limit + "");
                if (this.post) {
                    url.searchParams.append("post", this.post.id);
                }
                if (this.comment) {
                    url.searchParams.append("parent", this.comment.id);
                }
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows || []);
                });
            }

            onKeyUp(event) {
                if (event.key === "Enter") {
                    let value = this.formGroup.getValue();
                    let form = this.formGroup.get("form");
                    if (form) {
                        let text = form.get("text");
                        if (text) {
                            text.setValue("");
                        }
                    }
                    secureFetch("service/home/timeline/post/comments/comment", "POST", value.form).then(response => response.json()).then(response => {
                        this.list.add(value.form);
                    });
                }
            }
        }

        CommentsComponent.ɵfac = function CommentsComponent_Factory(t) {
            return new (t || CommentsComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsMetaFormService));
        };
        CommentsComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineComponent"]({
            type: CommentsComponent,
            selectors: [["app-comments"]],
            viewQuery: function CommentsComponent_Query(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵviewQuery"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsInfiniteScrollComponent, 5);
                }
                if (rf & 2) {
                    let _t;
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵloadQuery"]()) && (ctx.scroll = _t.first);
                }
            },
            inputs: {
                post: "post",
                comment: "comment"
            },
            decls: 5,
            vars: 2,
            consts: [["content", "", 2, "height", "fit-content"], [3, "items"], [3, "typedTemplate"], [3, "formGroup", 4, "ngIf"], [3, "model"], [3, "formGroup"], ["formGroupName", "form"], ["formControlName", "text", 3, "keyup"]],
            template: function CommentsComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](0, "div", 0)(1, "as-scroll-area")(2, "as-infinity-scroll", 1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵlistener"]("items", function CommentsComponent_Template_as_infinity_scroll_items_2_listener($event) {
                        return ctx.onLoad($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtemplate"](3, CommentsComponent_ng_template_3_Template, 1, 1, "ng-template", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtemplate"](4, CommentsComponent_form_4_Template, 3, 1, "form", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵproperty"]("typedTemplate", ctx.typeToken);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵproperty"]("ngIf", ctx.formGroup);
                }
            },
            dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_4__.NgIf, ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsScrollAreaComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsInfiniteScrollComponent, _angular_forms__WEBPACK_IMPORTED_MODULE_5__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_5__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControlName, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormGroupName, _typed_template_directive__WEBPACK_IMPORTED_MODULE_0__.TypedTemplateDirective, _comment_comment_component__WEBPACK_IMPORTED_MODULE_1__.CommentComponent],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 2127:
    /*!**************************************************************!*\
  !*** ./src/app/social/timeline/options/options.component.ts ***!
  \**************************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "OptionsComponent": () => (/* binding */ OptionsComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ng2-simplicity */ 4942);


        class OptionsComponent {
            constructor(window) {
                this.window = window;
            }

            onDocumentClick() {
                this.window.close();
            }

            onDeleteClick() {
                secureFetch(`service/home/timeline/post?id=${this.id}`, "DELETE").then(() => {
                    this.infinite.delete(this.id);
                });
            }
        }

        OptionsComponent.ɵfac = function OptionsComponent_Factory(t) {
            return new (t || OptionsComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsWindowComponent));
        };
        OptionsComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({
            type: OptionsComponent,
            selectors: [["app-options"]],
            hostBindings: function OptionsComponent_HostBindings(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function OptionsComponent_click_HostBindingHandler() {
                        return ctx.onDocumentClick();
                    }, false, _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵresolveDocument"]);
                }
            },
            decls: 7,
            vars: 0,
            consts: [["content", ""], [2, "cursor", "pointer", 3, "click"], [2, "display", "flex", "align-items", "center"], [1, "material-icons"], [2, "margin-left", "8px"]],
            template: function OptionsComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0)(1, "a", 1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function OptionsComponent_Template_a_click_1_listener() {
                        return ctx.onDeleteClick();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](2, "div", 2)(3, "span", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](4, "delete");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](5, "span", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](6, "Delete");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()()()();
                }
            },
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 632:
    /*!*************************************************************************!*\
  !*** ./src/app/social/timeline/post/image-post/image-post.component.ts ***!
  \*************************************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "ImagePostComponent": () => (/* binding */ ImagePostComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ 2508);


        class ImagePostComponent {
            constructor(service, window) {
                this.service = service;
                this.window = window;
            }

            ngOnInit() {
                this.form = this.service.create(this.model.$schema.properties, this.model);
            }

            onSubmit() {
                secureFetch("service/home/timeline/post", "POST", this.form.getValue()).then(response => {
                    this.window.close();
                });
            }
        }

        ImagePostComponent.ɵfac = function ImagePostComponent_Factory(t) {
            return new (t || ImagePostComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsMetaFormService), _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsDialogComponent));
        };
        ImagePostComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({
            type: ImagePostComponent,
            selectors: [["app-image-post"]],
            decls: 8,
            vars: 1,
            consts: [["content", ""], [3, "formGroup"], ["formGroupName", "form", 2, "background-color", "var(--main-background-color)"], ["formControlName", "editor", "name", "editor", 2, "width", "400px", "height", "280px"], ["formControlName", "image", 2, "width", "100%", "height", "280px"], ["footer", ""], ["type", "button", 3, "click"]],
            template: function ImagePostComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0)(1, "form", 1)(2, "div", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](3, "as-editor", 3)(4, "as-image-upload", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](5, "div", 5)(6, "button", 6);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function ImagePostComponent_Template_button_click_6_listener() {
                        return ctx.onSubmit();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](7, "Okay");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("formGroup", ctx.form);
                }
            },
            dependencies: [ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsEditorComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsImageUploadComponent, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormControlName, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormGroupName],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 3609:
    /*!********************************************************!*\
  !*** ./src/app/social/timeline/post/post.component.ts ***!
  \********************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "PostComponent": () => (/* binding */ PostComponent)
            /* harmony export */
        });
        /* harmony import */
        var _options_options_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../options/options.component */ 2127);
        /* harmony import */
        var _shared_likes_likes_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../shared/likes/likes.component */ 27);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_common__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/common */ 4666);
        /* harmony import */
        var _comments_comments_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../comments/comments.component */ 5384);
        /* harmony import */
        var _shared_humanize_pipe__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../shared/humanize.pipe */ 5804);


        const _c0 = ["video"];

        function PostComponent_img_15_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](0, "img", 20);
            }
            if (rf & 2) {
                const ctx_r0 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("src", ctx_r0.model.image.url, _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵsanitizeUrl"]);
            }
        }

        function PostComponent_div_16_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 21);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](1, "img", 22);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
            }
            if (rf & 2) {
                const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("src", "service/shared/media/" + ctx_r1.model.video.thumbnail.id, _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵsanitizeUrl"]);
            }
        }

        class PostComponent {
            constructor(windowManager, elementRef) {
                this.windowManager = windowManager;
                this.elementRef = elementRef;
            }

            ngAfterViewInit() {
                if (this.video) {
                    this.video.nativeElement.pause();
                }
            }

            onLike(post) {
                this.likes.model.likes.active = !this.likes.model.likes.active;
                let method = "POST";
                secureFetch("service/home/timeline/post", method, post).then(() => {
                    if (this.likes.model.likes.active) {
                        this.likes.model.likes.count++;
                    } else {
                        this.likes.model.likes.count--;
                    }
                });
            }

            onOptionsClick(event, id) {
                event.stopPropagation();
                let windowRef = this.windowManager.create(_options_options_component__WEBPACK_IMPORTED_MODULE_0__.OptionsComponent, {
                    header: "Options",
                    width: "100px",
                    top: event.clientY + "px",
                    left: event.clientX - 100 + "px"
                });
                windowRef.instance.infinite = this.infinite;
                windowRef.instance.id = id;
            }
        }

        PostComponent.ɵfac = function PostComponent_Factory(t) {
            return new (t || PostComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_5__.WindowManagerService), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_4__.ElementRef));
        };
        PostComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdefineComponent"]({
            type: PostComponent,
            selectors: [["app-post"]],
            viewQuery: function PostComponent_Query(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵviewQuery"](_shared_likes_likes_component__WEBPACK_IMPORTED_MODULE_1__.LikesComponent, 5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵviewQuery"](_c0, 5);
                }
                if (rf & 2) {
                    let _t;
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵloadQuery"]()) && (ctx.likes = _t.first);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵloadQuery"]()) && (ctx.video = _t.first);
                }
            },
            inputs: {
                model: "model",
                infinite: "infinite"
            },
            decls: 30,
            vars: 10,
            consts: [[2, "max-width", "680px", "min-width", "400px", "background-color", "var(--main-dark2-color)", "padding", "10px", "margin-bottom", "20px"], [2, "display", "flex", "align-items", "center", "margin-bottom", "5px"], [2, "max-width", "48px", "max-height", "48px", "border-radius", "50%", 3, "src"], [2, "display", "flex", "width", "100%"], [2, "margin-left", "5px"], [2, "font-size", "small"], [2, "flex", "1"], [2, "position", "relative"], ["type", "button", 1, "material-icons", 3, "click"], [2, "margin-bottom", "5px", 3, "innerHTML"], ["style", "width: 100%", 3, "src", 4, "ngIf"], ["style", "text-align: center", 4, "ngIf"], [2, "margin-top", "10px", "margin-bottom", "10px"], [3, "model"], [2, "background-color", "var(--main-dark1-color)"], [2, "display", "flex", "justify-content", "space-around", "align-items", "center", "margin-top", "10px"], [1, "normal", 3, "click"], [1, "material-icons", 2, "font-size", "small", "vertical-align", "-2px", "margin-right", "5px"], [1, "normal"], [3, "post"], [2, "width", "100%", 3, "src"], [2, "text-align", "center"], [2, "height", "400px", 3, "src"]],
            template: function PostComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "div", 0)(1, "div", 1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](2, "img", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](3, "div", 3)(4, "div", 4)(5, "div");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](6);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](7, "div", 5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](8);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵpipe"](9, "humanize");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](10, "div", 6);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](11, "div", 7)(12, "button", 8);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function PostComponent_Template_button_click_12_listener($event) {
                        return ctx.onOptionsClick($event, ctx.model.id);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](13, "more_vert");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](14, "div", 9);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](15, PostComponent_img_15_Template, 1, 1, "img", 10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](16, PostComponent_div_16_Template, 2, 1, "div", 11);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](17, "div", 12);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](18, "app-likes", 13);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](19, "hr", 14);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](20, "div", 15)(21, "button", 16);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function PostComponent_Template_button_click_21_listener() {
                        return ctx.onLike(ctx.model);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](22, "span", 17);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](23, "thumb_up");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](24, "I Like ");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](25, "button", 18);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](26, "Comment");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](27, "button", 18);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](28, "Share");
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](29, "app-comments", 19);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("src", ctx.model.owner.picture.url, _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵsanitizeUrl"]);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](ctx.model.owner.name);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵpipeBind1"](9, 8, ctx.model.created));
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](6);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("innerHTML", ctx.model.editor.html, _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵsanitizeHtml"]);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx.model.image);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngIf", ctx.model.video);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("model", ctx.model);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](11);
                    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("post", ctx.model);
                }
            },
            dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_6__.NgIf, _shared_likes_likes_component__WEBPACK_IMPORTED_MODULE_1__.LikesComponent, _comments_comments_component__WEBPACK_IMPORTED_MODULE_2__.CommentsComponent, _shared_humanize_pipe__WEBPACK_IMPORTED_MODULE_3__.HumanizePipe],
            styles: ["app-post {\r\n  display: block;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvc29jaWFsL3RpbWVsaW5lL3Bvc3QvcG9zdC5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsY0FBYztBQUNoQiIsInNvdXJjZXNDb250ZW50IjpbImFwcC1wb3N0IHtcclxuICBkaXNwbGF5OiBibG9jaztcclxufVxyXG4iXSwic291cmNlUm9vdCI6IiJ9 */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 897:
    /*!***********************************************************************!*\
  !*** ./src/app/social/timeline/post/text-post/text-post.component.ts ***!
  \***********************************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "TextPostComponent": () => (/* binding */ TextPostComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ 2508);


        class TextPostComponent {
            constructor(service, window) {
                this.service = service;
                this.window = window;
            }

            ngOnInit() {
                this.form = this.service.create(this.model.$schema.properties, this.model);
            }

            onSubmit() {
                secureFetch("service/home/timeline/post", "POST", this.form.getValue()).then(response => {
                    this.window.close();
                });
            }
        }

        TextPostComponent.ɵfac = function TextPostComponent_Factory(t) {
            return new (t || TextPostComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsMetaFormService), _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsDialogComponent));
        };
        TextPostComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({
            type: TextPostComponent,
            selectors: [["app-text-post"]],
            decls: 7,
            vars: 1,
            consts: [["content", ""], [3, "formGroup"], ["formGroupName", "form", 2, "background-color", "var(--main-background-color)"], ["formControlName", "editor", "name", "editor", 2, "width", "400px", "height", "280px"], ["footer", ""], ["type", "button", 3, "click"]],
            template: function TextPostComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0)(1, "form", 1)(2, "div", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](3, "as-editor", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](4, "div", 4)(5, "button", 5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function TextPostComponent_Template_button_click_5_listener() {
                        return ctx.onSubmit();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](6, "Okay");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("formGroup", ctx.form);
                }
            },
            dependencies: [ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsEditorComponent, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormControlName, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormGroupName],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 4749:
    /*!*************************************************************************!*\
  !*** ./src/app/social/timeline/post/video-post/video-post.component.ts ***!
  \*************************************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "VideoPostComponent": () => (/* binding */ VideoPostComponent)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ 2508);


        class VideoPostComponent {
            constructor(service, window) {
                this.service = service;
                this.window = window;
                this.progress = 0;
            }

            ngOnInit() {
                this.form = this.service.create(this.model.$schema.properties, this.model);
            }

            onSubmit() {
                let request = new XMLHttpRequest();
                request.open("POST", "service/home/timeline/post");
                request.setRequestHeader("Content-Type", "application/json");
                request.upload.addEventListener('progress', event => {
                    this.progress = Math.round(event.loaded / event.total * 100);
                });
                request.addEventListener("loadend", () => {
                    this.window.close();
                });
                request.send(JSON.stringify(this.form.getValue()));
            }
        }

        VideoPostComponent.ɵfac = function VideoPostComponent_Factory(t) {
            return new (t || VideoPostComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsMetaFormService), _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsDialogComponent));
        };
        VideoPostComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({
            type: VideoPostComponent,
            selectors: [["app-video-post"]],
            decls: 12,
            vars: 2,
            consts: [["content", ""], [3, "formGroup"], ["formGroupName", "form", 2, "background-color", "var(--main-background-color)"], ["formControlName", "editor", "name", "editor", 2, "width", "400px", "height", "280px"], ["formControlName", "video", 2, "width", "400px", "height", "280px"], ["footer", ""], [2, "display", "flex"], ["type", "button", 3, "click"], [2, "flex", "1"]],
            template: function VideoPostComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0)(1, "form", 1)(2, "div", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](3, "as-editor", 3)(4, "as-video-upload", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](5, "div", 5)(6, "div", 6)(7, "button", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function VideoPostComponent_Template_button_click_7_listener() {
                        return ctx.onSubmit();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](8, "Okay");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](9, "div", 8);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](10, "div");
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](11);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]()()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("formGroup", ctx.form);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate1"]("", ctx.progress, " Percent uploaded");
                }
            },
            dependencies: [ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsEditorComponent, ng2_simplicity__WEBPACK_IMPORTED_MODULE_1__.AsVideoUploadComponent, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormControlName, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.FormGroupName],
            styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 4639:
    /*!*******************************************************!*\
  !*** ./src/app/social/timeline/timeline.component.ts ***!
  \*******************************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "TimelineComponent": () => (/* binding */ TimelineComponent)
            /* harmony export */
        });
        /* harmony import */
        var ng2_simplicity__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ng2-simplicity */ 4942);
        /* harmony import */
        var _post_text_post_text_post_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./post/text-post/text-post.component */ 897);
        /* harmony import */
        var _post_image_post_image_post_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./post/image-post/image-post.component */ 632);
        /* harmony import */
        var _post_video_post_video_post_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./post/video-post/video-post.component */ 4749);
        /* harmony import */
        var _rest_classes__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../rest.classes */ 2235);
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 2560);
        /* harmony import */
        var _app_startup_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../app-startup.service */ 9609);


        function TimelineComponent_ng_template_27_Template(rf, ctx) {
            if (rf & 1) {
                _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "app-post", 13);
            }
            if (rf & 2) {
                const item_r1 = ctx.$implicit;
                const ctx_r0 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
                _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("model", item_r1)("infinite", ctx_r0.infinite);
            }
        }

        class TimelineComponent {
            constructor(service, windowManager) {
                this.service = service;
                this.windowManager = windowManager;
                this.owner = service.model.form.id;
            }

            postsLoader(event) {
                let link = "service/home/timeline";
                const url = new URL(window.location.origin + "/" + link);
                url.searchParams.append("index", event.query.index + "");
                url.searchParams.append("limit", event.query.limit + "");
                if (this.owner) {
                    url.searchParams.append("owner", this.owner);
                }
                secureFetch(url.toString()).then(response => response.json()).then(response => {
                    event.callback(response.rows || []);
                });
            }

            onVideoPostClick() {
                this.onPostClick("video", _post_video_post_video_post_component__WEBPACK_IMPORTED_MODULE_2__.VideoPostComponent);
            }

            onImagePostClick() {
                this.onPostClick("image", _post_image_post_image_post_component__WEBPACK_IMPORTED_MODULE_1__.ImagePostComponent);
            }

            onTextPostClick() {
                this.onPostClick("text", _post_text_post_text_post_component__WEBPACK_IMPORTED_MODULE_0__.TextPostComponent);
            }

            onPostClick(value, component) {
                secureFetch(`service/home/timeline/post/create?type=${value}&source=${this.owner}`).then(response => response.json()).then(response => {
                    let windowRef = this.windowManager.create(component, {
                        header: "Post",
                        dialog: true,
                        width: "400px"
                    });
                    windowRef.instance.model = response;
                });
            }
        }

        TimelineComponent.ɵfac = function TimelineComponent_Factory(t) {
            return new (t || TimelineComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_app_startup_service__WEBPACK_IMPORTED_MODULE_4__.AppStartupService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_6__.WindowManagerService));
        };
        TimelineComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
            type: TimelineComponent,
            selectors: [["app-timeline"]],
            viewQuery: function TimelineComponent_Query(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](ng2_simplicity__WEBPACK_IMPORTED_MODULE_6__.AsInfiniteScrollComponent, 5);
                }
                if (rf & 2) {
                    let _t;
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.infinite = _t.first);
                }
            },
            inputs: {
                owner: "owner"
            },
            decls: 28,
            vars: 3,
            consts: [[2, "display", "flex", "justify-content", "center"], [2, "width", "max(400px, 680px)", "margin-bottom", "16px", "background-color", "var(--main-dark2-color)", "padding", "8px"], [2, "display", "flex"], [2, "width", "48px", "height", "48px", "border-radius", "50%", "margin-right", "8px", 3, "src"], [2, "flex", "1", 3, "placeholder"], ["type", "text", "ngModel", ""], [2, "display", "flex", "justify-content", "space-around", "gap", "10px"], [1, "normal", "link", 3, "click"], [1, "container"], [1, "material-icons"], [1, "name"], [3, "items"], [3, "typedTemplate"], [3, "model", "infinite"]],
            template: function TimelineComponent_Template(rf, ctx) {
                if (rf & 1) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "div", 0)(1, "div", 1)(2, "div", 2);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](3, "img", 3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](4, "as-input-container", 4);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](5, "input", 5);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](6, "div", 6)(7, "a", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function TimelineComponent_Template_a_click_7_listener() {
                        return ctx.onImagePostClick();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](8, "div", 8)(9, "span", 9);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](10, "image");
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](11, "span", 10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](12, "Image");
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](13, "a", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function TimelineComponent_Template_a_click_13_listener() {
                        return ctx.onVideoPostClick();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](14, "div", 8)(15, "span", 9);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](16, "videocam");
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](17, "span", 10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](18, "Video");
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](19, "a", 7);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function TimelineComponent_Template_a_click_19_listener() {
                        return ctx.onTextPostClick();
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](20, "div", 8)(21, "span", 9);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](22, "description");
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](23, "span", 10);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](24, "Text");
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()()()();
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](25, "div", 0)(26, "as-infinity-scroll", 11);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("items", function TimelineComponent_Template_as_infinity_scroll_items_26_listener($event) {
                        return ctx.postsLoader($event);
                    });
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](27, TimelineComponent_ng_template_27_Template, 1, 2, "ng-template", 12);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
                }
                if (rf & 2) {
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("src", ctx.service.model.form.picture.url, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵsanitizeUrl"]);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](1);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("placeholder", "What do you do, " + ctx.service.model.form.nickName + "?");
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](23);
                    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("typedTemplate", ctx.typeToken);
                }
            },
            styles: ["app-timeline {\r\n  display: block;\r\n}\r\n\r\napp-timeline span.name {\r\n  margin-left: 8px;\r\n}\r\n\r\napp-timeline div.container {\r\n  display: flex;\r\n  align-items: center;\r\n  justify-content: center;\r\n}\r\n\r\napp-timeline a.link {\r\n  flex: 1;\r\n  cursor: pointer;\r\n}\r\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvc29jaWFsL3RpbWVsaW5lL3RpbWVsaW5lLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxjQUFjO0FBQ2hCOztBQUVBO0VBQ0UsZ0JBQWdCO0FBQ2xCOztBQUVBO0VBQ0UsYUFBYTtFQUNiLG1CQUFtQjtFQUNuQix1QkFBdUI7QUFDekI7O0FBRUE7RUFDRSxPQUFPO0VBQ1AsZUFBZTtBQUNqQiIsInNvdXJjZXNDb250ZW50IjpbImFwcC10aW1lbGluZSB7XHJcbiAgZGlzcGxheTogYmxvY2s7XHJcbn1cclxuXHJcbmFwcC10aW1lbGluZSBzcGFuLm5hbWUge1xyXG4gIG1hcmdpbi1sZWZ0OiA4cHg7XHJcbn1cclxuXHJcbmFwcC10aW1lbGluZSBkaXYuY29udGFpbmVyIHtcclxuICBkaXNwbGF5OiBmbGV4O1xyXG4gIGFsaWduLWl0ZW1zOiBjZW50ZXI7XHJcbiAganVzdGlmeS1jb250ZW50OiBjZW50ZXI7XHJcbn1cclxuXHJcbmFwcC10aW1lbGluZSBhLmxpbmsge1xyXG4gIGZsZXg6IDE7XHJcbiAgY3Vyc29yOiBwb2ludGVyO1xyXG59XHJcbiJdLCJzb3VyY2VSb290IjoiIn0= */"],
            encapsulation: 2
        });

        /***/
    }),

    /***/ 6538:
    /*!*********************************************!*\
  !*** ./src/app/typed-template.directive.ts ***!
  \*********************************************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony export */
        __webpack_require__.d(__webpack_exports__, {
            /* harmony export */   "TypedTemplateDirective": () => (/* binding */ TypedTemplateDirective)
            /* harmony export */
        });
        /* harmony import */
        var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);

        class TypedTemplateDirective {
            constructor(contentTemplate) {
                this.contentTemplate = contentTemplate;
            }

            static ngTemplateContextGuard(dir, ctx) {
                return true;
            }
        }

        TypedTemplateDirective.ɵfac = function TypedTemplateDirective_Factory(t) {
            return new (t || TypedTemplateDirective)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_0__.TemplateRef));
        };
        TypedTemplateDirective.ɵdir = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineDirective"]({
            type: TypedTemplateDirective,
            selectors: [["ng-template", "typedTemplate", ""]],
            inputs: {
                typedTemplate: "typedTemplate"
            }
        });

        /***/
    }),

    /***/ 4431:
    /*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
    /***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

        "use strict";
        __webpack_require__.r(__webpack_exports__);
        /* harmony import */
        var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ 4497);
        /* harmony import */
        var _app_app_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app/app.module */ 6747);


        _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__.platformBrowser().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_0__.AppModule).catch(err => console.error(err));

        /***/
    }),

    /***/ 6700:
    /*!***************************************************!*\
  !*** ./node_modules/moment/locale/ sync ^\.\/.*$ ***!
  \***************************************************/
    /***/ ((module, __unused_webpack_exports, __webpack_require__) => {

        var map = {
            "./af": 8685,
            "./af.js": 8685,
            "./ar": 254,
            "./ar-dz": 4312,
            "./ar-dz.js": 4312,
            "./ar-kw": 2614,
            "./ar-kw.js": 2614,
            "./ar-ly": 8630,
            "./ar-ly.js": 8630,
            "./ar-ma": 8674,
            "./ar-ma.js": 8674,
            "./ar-sa": 9032,
            "./ar-sa.js": 9032,
            "./ar-tn": 4730,
            "./ar-tn.js": 4730,
            "./ar.js": 254,
            "./az": 3052,
            "./az.js": 3052,
            "./be": 150,
            "./be.js": 150,
            "./bg": 8080,
            "./bg.js": 8080,
            "./bm": 3466,
            "./bm.js": 3466,
            "./bn": 8516,
            "./bn-bd": 557,
            "./bn-bd.js": 557,
            "./bn.js": 8516,
            "./bo": 6273,
            "./bo.js": 6273,
            "./br": 9588,
            "./br.js": 9588,
            "./bs": 9815,
            "./bs.js": 9815,
            "./ca": 3331,
            "./ca.js": 3331,
            "./cs": 1320,
            "./cs.js": 1320,
            "./cv": 2219,
            "./cv.js": 2219,
            "./cy": 8266,
            "./cy.js": 8266,
            "./da": 6427,
            "./da.js": 6427,
            "./de": 7435,
            "./de-at": 2871,
            "./de-at.js": 2871,
            "./de-ch": 2994,
            "./de-ch.js": 2994,
            "./de.js": 7435,
            "./dv": 2357,
            "./dv.js": 2357,
            "./el": 5649,
            "./el.js": 5649,
            "./en-au": 9961,
            "./en-au.js": 9961,
            "./en-ca": 9878,
            "./en-ca.js": 9878,
            "./en-gb": 3924,
            "./en-gb.js": 3924,
            "./en-ie": 864,
            "./en-ie.js": 864,
            "./en-il": 1579,
            "./en-il.js": 1579,
            "./en-in": 940,
            "./en-in.js": 940,
            "./en-nz": 6181,
            "./en-nz.js": 6181,
            "./en-sg": 4301,
            "./en-sg.js": 4301,
            "./eo": 5291,
            "./eo.js": 5291,
            "./es": 4529,
            "./es-do": 3764,
            "./es-do.js": 3764,
            "./es-mx": 2584,
            "./es-mx.js": 2584,
            "./es-us": 3425,
            "./es-us.js": 3425,
            "./es.js": 4529,
            "./et": 5203,
            "./et.js": 5203,
            "./eu": 678,
            "./eu.js": 678,
            "./fa": 3483,
            "./fa.js": 3483,
            "./fi": 6262,
            "./fi.js": 6262,
            "./fil": 2521,
            "./fil.js": 2521,
            "./fo": 4555,
            "./fo.js": 4555,
            "./fr": 3131,
            "./fr-ca": 8239,
            "./fr-ca.js": 8239,
            "./fr-ch": 1702,
            "./fr-ch.js": 1702,
            "./fr.js": 3131,
            "./fy": 267,
            "./fy.js": 267,
            "./ga": 3821,
            "./ga.js": 3821,
            "./gd": 1753,
            "./gd.js": 1753,
            "./gl": 4074,
            "./gl.js": 4074,
            "./gom-deva": 2762,
            "./gom-deva.js": 2762,
            "./gom-latn": 5969,
            "./gom-latn.js": 5969,
            "./gu": 2809,
            "./gu.js": 2809,
            "./he": 5402,
            "./he.js": 5402,
            "./hi": 315,
            "./hi.js": 315,
            "./hr": 410,
            "./hr.js": 410,
            "./hu": 8288,
            "./hu.js": 8288,
            "./hy-am": 7928,
            "./hy-am.js": 7928,
            "./id": 1334,
            "./id.js": 1334,
            "./is": 6959,
            "./is.js": 6959,
            "./it": 4864,
            "./it-ch": 1124,
            "./it-ch.js": 1124,
            "./it.js": 4864,
            "./ja": 6141,
            "./ja.js": 6141,
            "./jv": 9187,
            "./jv.js": 9187,
            "./ka": 2136,
            "./ka.js": 2136,
            "./kk": 4332,
            "./kk.js": 4332,
            "./km": 8607,
            "./km.js": 8607,
            "./kn": 4305,
            "./kn.js": 4305,
            "./ko": 234,
            "./ko.js": 234,
            "./ku": 6003,
            "./ku.js": 6003,
            "./ky": 5061,
            "./ky.js": 5061,
            "./lb": 2786,
            "./lb.js": 2786,
            "./lo": 6183,
            "./lo.js": 6183,
            "./lt": 29,
            "./lt.js": 29,
            "./lv": 4169,
            "./lv.js": 4169,
            "./me": 8577,
            "./me.js": 8577,
            "./mi": 8177,
            "./mi.js": 8177,
            "./mk": 337,
            "./mk.js": 337,
            "./ml": 5260,
            "./ml.js": 5260,
            "./mn": 2325,
            "./mn.js": 2325,
            "./mr": 4695,
            "./mr.js": 4695,
            "./ms": 5334,
            "./ms-my": 7151,
            "./ms-my.js": 7151,
            "./ms.js": 5334,
            "./mt": 3570,
            "./mt.js": 3570,
            "./my": 7963,
            "./my.js": 7963,
            "./nb": 8028,
            "./nb.js": 8028,
            "./ne": 6638,
            "./ne.js": 6638,
            "./nl": 302,
            "./nl-be": 6782,
            "./nl-be.js": 6782,
            "./nl.js": 302,
            "./nn": 3501,
            "./nn.js": 3501,
            "./oc-lnc": 563,
            "./oc-lnc.js": 563,
            "./pa-in": 869,
            "./pa-in.js": 869,
            "./pl": 5302,
            "./pl.js": 5302,
            "./pt": 9687,
            "./pt-br": 4884,
            "./pt-br.js": 4884,
            "./pt.js": 9687,
            "./ro": 9107,
            "./ro.js": 9107,
            "./ru": 3627,
            "./ru.js": 3627,
            "./sd": 355,
            "./sd.js": 355,
            "./se": 3427,
            "./se.js": 3427,
            "./si": 1848,
            "./si.js": 1848,
            "./sk": 4590,
            "./sk.js": 4590,
            "./sl": 184,
            "./sl.js": 184,
            "./sq": 6361,
            "./sq.js": 6361,
            "./sr": 8965,
            "./sr-cyrl": 1287,
            "./sr-cyrl.js": 1287,
            "./sr.js": 8965,
            "./ss": 5456,
            "./ss.js": 5456,
            "./sv": 451,
            "./sv.js": 451,
            "./sw": 7558,
            "./sw.js": 7558,
            "./ta": 1356,
            "./ta.js": 1356,
            "./te": 3693,
            "./te.js": 3693,
            "./tet": 1243,
            "./tet.js": 1243,
            "./tg": 2500,
            "./tg.js": 2500,
            "./th": 5768,
            "./th.js": 5768,
            "./tk": 7761,
            "./tk.js": 7761,
            "./tl-ph": 5780,
            "./tl-ph.js": 5780,
            "./tlh": 9590,
            "./tlh.js": 9590,
            "./tr": 3807,
            "./tr.js": 3807,
            "./tzl": 3857,
            "./tzl.js": 3857,
            "./tzm": 654,
            "./tzm-latn": 8806,
            "./tzm-latn.js": 8806,
            "./tzm.js": 654,
            "./ug-cn": 845,
            "./ug-cn.js": 845,
            "./uk": 9232,
            "./uk.js": 9232,
            "./ur": 7052,
            "./ur.js": 7052,
            "./uz": 7967,
            "./uz-latn": 2233,
            "./uz-latn.js": 2233,
            "./uz.js": 7967,
            "./vi": 8615,
            "./vi.js": 8615,
            "./x-pseudo": 2320,
            "./x-pseudo.js": 2320,
            "./yo": 1313,
            "./yo.js": 1313,
            "./zh-cn": 4490,
            "./zh-cn.js": 4490,
            "./zh-hk": 5910,
            "./zh-hk.js": 5910,
            "./zh-mo": 8262,
            "./zh-mo.js": 8262,
            "./zh-tw": 4223,
            "./zh-tw.js": 4223
        };


        function webpackContext(req) {
            var id = webpackContextResolve(req);
            return __webpack_require__(id);
        }

        function webpackContextResolve(req) {
            if (!__webpack_require__.o(map, req)) {
                var e = new Error("Cannot find module '" + req + "'");
                e.code = 'MODULE_NOT_FOUND';
                throw e;
            }
            return map[req];
        }

        webpackContext.keys = function webpackContextKeys() {
            return Object.keys(map);
        };
        webpackContext.resolve = webpackContextResolve;
        module.exports = webpackContext;
        webpackContext.id = 6700;

        /***/
    })

},
    /******/ __webpack_require__ => { // webpackRuntimeModules
        /******/
        var __webpack_exec__ = (moduleId) => (__webpack_require__(__webpack_require__.s = moduleId))
        /******/
        __webpack_require__.O(0, ["vendor"], () => (__webpack_exec__(4431)));
        /******/
        var __webpack_exports__ = __webpack_require__.O();
        /******/
    }
]);
//# sourceMappingURL=main.js.map