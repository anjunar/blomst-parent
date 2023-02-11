"use strict";
(self["webpackChunkblomst"] = self["webpackChunkblomst"] || []).push([["main"],{

/***/ 7386:
/*!*************************************************!*\
  !*** ./src/app/app-routing-strategy.service.ts ***!
  \*************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppRoutingStrategyService": () => (/* binding */ AppRoutingStrategyService)
/* harmony export */ });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);


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

/***/ }),

/***/ 158:
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppRoutingModule": () => (/* binding */ AppRoutingModule)
/* harmony export */ });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var _navigator_app_navigator_form_app_navigator_form_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./navigator/app-navigator-form/app-navigator-form.component */ 7807);
/* harmony import */ var _navigator_app_navigator_resolver__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./navigator/app-navigator.resolver */ 3854);
/* harmony import */ var _navigator_app_navigator_table_app_navigator_table_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./navigator/app-navigator-table/app-navigator-table.component */ 3395);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ 2560);






const routes = [{
  path: "navigator/form",
  runGuardsAndResolvers: 'always',
  component: _navigator_app_navigator_form_app_navigator_form_component__WEBPACK_IMPORTED_MODULE_0__.AppNavigatorFormComponent,
  resolve: {
    model: _navigator_app_navigator_resolver__WEBPACK_IMPORTED_MODULE_1__.AppNavigatorResolver
  }
}, {
  path: "navigator/table",
  component: _navigator_app_navigator_table_app_navigator_table_component__WEBPACK_IMPORTED_MODULE_2__.AppNavigatorTableComponent
}];
class AppRoutingModule {}
AppRoutingModule.ɵfac = function AppRoutingModule_Factory(t) {
  return new (t || AppRoutingModule)();
};
AppRoutingModule.ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdefineNgModule"]({
  type: AppRoutingModule
});
AppRoutingModule.ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdefineInjector"]({
  imports: [_angular_router__WEBPACK_IMPORTED_MODULE_4__.RouterModule.forRoot(routes), _angular_router__WEBPACK_IMPORTED_MODULE_4__.RouterModule]
});
(function () {
  (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵsetNgModuleScope"](AppRoutingModule, {
    imports: [_angular_router__WEBPACK_IMPORTED_MODULE_4__.RouterModule],
    exports: [_angular_router__WEBPACK_IMPORTED_MODULE_4__.RouterModule]
  });
})();

/***/ }),

/***/ 5041:
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppComponent": () => (/* binding */ AppComponent)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _navigator_app_navigator_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./navigator/app-navigator.service */ 1341);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var angular2_simplicity__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! angular2-simplicity */ 9533);





const _c0 = function (a0) {
  return [a0];
};
const _c1 = function (a0) {
  return {
    link: a0
  };
};
function AppComponent_li_23_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "li")(1, "a", 21);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const entry_r1 = ctx.$implicit;
    const ctx_r0 = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("routerLink", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpureFunction1"](3, _c0, "/navigator/" + entry_r1.value.type))("queryParams", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpureFunction1"](5, _c1, ctx_r0.toBase64(entry_r1.value.url)));
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate"](entry_r1.key);
  }
}
class AppComponent {
  constructor(navigator) {
    this.navigator = navigator;
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
  }
  get service() {
    return btoa("service");
  }
  get links() {
    return this.navigator.links;
  }
  toBase64(value) {
    return btoa(value);
  }
}
AppComponent.ɵfac = function AppComponent_Factory(t) {
  return new (t || AppComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_navigator_app_navigator_service__WEBPACK_IMPORTED_MODULE_0__.AppNavigatorService));
};
AppComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
  type: AppComponent,
  selectors: [["app-root"]],
  decls: 30,
  vars: 9,
  consts: [[2, "height", "38px"], [2, "box-shadow", "0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)", "position", "absolute", "z-index", "9999"], ["left", "", 2, "display", "flex"], [1, "material-icons", 3, "click"], ["routerLink", "/navigator/form", "routerLinkActive", "active", 1, "normal", 3, "queryParams"], ["middle", ""], ["right", ""], [1, "browsers"], ["href", "https://www.google.de/chrome", "target", "_blank"], ["src", "assets/app-root/chrome_logo.png", 2, "height", "32px", "width", "32px", "margin-right", "5px", "vertical-align", "bottom"], ["href", "https://www.microsoft.com/de-de/edge", "target", "_blank"], ["src", "assets/app-root/edge.png", 2, "height", "32px", "width", "32px", "margin-right", "12px", "vertical-align", "bottom"], ["href", "https://www.mozilla.org", "target", "_blank"], ["src", "assets/app-root/firefox_logo.png", 2, "height", "32px", "width", "32px", "margin-right", "12px", "vertical-align", "bottom"], ["href", "https://github.com/anjunar/simplicity", "target", "_blank"], ["src", "assets/app-root/github-circle-white-transparent.svg"], [2, "height", "calc(100% - 38px)"], [3, "open"], [4, "ngFor", "ngForOf"], [2, "position", "relative", "height", "calc(100% - 38px)"], ["left", ""], [3, "routerLink", "queryParams"]],
  template: function AppComponent_Template(rf, ctx) {
    if (rf & 1) {
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](0, "div", 0);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](1, "as-toolbar", 1)(2, "div", 2)(3, "a", 3);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("click", function AppComponent_Template_a_click_3_listener() {
        return ctx.open = !ctx.open;
      });
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](4, "home");
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](5, "a", 4);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](6, "Navigator");
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](7, "div", 5);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](8);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](9, "div", 6)(10, "div", 7)(11, "a", 8);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](12, "img", 9);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](13, "a", 10);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](14, "img", 11);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](15, "a", 12);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](16, "img", 13);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](17, "a", 14);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](18, "img", 15);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()()();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](19, "as-drawer-container", 16)(20, "as-drawer", 17)(21, "nav")(22, "ul");
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtemplate"](23, AppComponent_li_23_Template, 3, 7, "li", 18);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpipe"](24, "keyvalue");
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](25, "as-drawer-content")(26, "as-viewport", 19);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](27, "router-outlet");
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()();
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](28, "as-footer");
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelement"](29, "as-taskbar", 20);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
    }
    if (rf & 2) {
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](5);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("queryParams", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpureFunction1"](7, _c1, ctx.service));
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](3);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate"](ctx.name);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](12);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("open", ctx.open);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](3);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("ngForOf", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpipeBind2"](24, 4, ctx.links, ctx.originalOrder));
    }
  },
  dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_2__.NgForOf, _angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterOutlet, _angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterLink, _angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterLinkActive, angular2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsToolbarComponent, angular2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsDrawerComponent, angular2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsDrawerContainerComponent, angular2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsDrawerContentComponent, angular2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsFooterComponent, angular2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsTaskbarComponent, angular2_simplicity__WEBPACK_IMPORTED_MODULE_4__.AsViewportComponent, _angular_common__WEBPACK_IMPORTED_MODULE_2__.KeyValuePipe],
  styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
  encapsulation: 2
});

/***/ }),

/***/ 6747:
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppModule": () => (/* binding */ AppModule)
/* harmony export */ });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/platform-browser */ 4497);
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app-routing.module */ 158);
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./app.component */ 5041);
/* harmony import */ var angular2_simplicity__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! angular2-simplicity */ 9533);
/* harmony import */ var _navigator_app_navigator_form_app_navigator_form_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./navigator/app-navigator-form/app-navigator-form.component */ 7807);
/* harmony import */ var _navigator_app_navigator_table_app_navigator_table_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./navigator/app-navigator-table/app-navigator-table.component */ 3395);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var _app_routing_strategy_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./app-routing-strategy.service */ 7386);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 2560);









class AppModule {}
AppModule.ɵfac = function AppModule_Factory(t) {
  return new (t || AppModule)();
};
AppModule.ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineNgModule"]({
  type: AppModule,
  bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_1__.AppComponent]
});
AppModule.ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineInjector"]({
  providers: [{
    provide: _angular_router__WEBPACK_IMPORTED_MODULE_6__.RouteReuseStrategy,
    useClass: _app_routing_strategy_service__WEBPACK_IMPORTED_MODULE_4__.AppRoutingStrategyService
  }],
  imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_7__.BrowserModule, _app_routing_module__WEBPACK_IMPORTED_MODULE_0__.AppRoutingModule, angular2_simplicity__WEBPACK_IMPORTED_MODULE_8__.Angular2SimplicityModule]
});
(function () {
  (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵsetNgModuleScope"](AppModule, {
    declarations: [_app_component__WEBPACK_IMPORTED_MODULE_1__.AppComponent, _navigator_app_navigator_form_app_navigator_form_component__WEBPACK_IMPORTED_MODULE_2__.AppNavigatorFormComponent, _navigator_app_navigator_table_app_navigator_table_component__WEBPACK_IMPORTED_MODULE_3__.AppNavigatorTableComponent],
    imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_7__.BrowserModule, _app_routing_module__WEBPACK_IMPORTED_MODULE_0__.AppRoutingModule, angular2_simplicity__WEBPACK_IMPORTED_MODULE_8__.Angular2SimplicityModule]
  });
})();

/***/ }),

/***/ 7807:
/*!******************************************************************************!*\
  !*** ./src/app/navigator/app-navigator-form/app-navigator-form.component.ts ***!
  \******************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppNavigatorFormComponent": () => (/* binding */ AppNavigatorFormComponent)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var _app_navigator_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../app-navigator.service */ 1341);
/* harmony import */ var angular2_simplicity__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! angular2-simplicity */ 9533);




class AppNavigatorFormComponent {
  constructor(router, route, service) {
    this.router = router;
    this.route = route;
    this.service = service;
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
  onSubmit(event) {
    let body = JSON.stringify(event.model);
    let method = event.link.value.method;
    let headers = {
      "content-type": "application/json"
    };
    fetch(event.link.value.url, {
      method: method,
      body: body,
      headers: headers
    }).then(response => response.json()).then(response => {
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
AppNavigatorFormComponent.ɵfac = function AppNavigatorFormComponent_Factory(t) {
  return new (t || AppNavigatorFormComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.ActivatedRoute), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_app_navigator_service__WEBPACK_IMPORTED_MODULE_0__.AppNavigatorService));
};
AppNavigatorFormComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
  type: AppNavigatorFormComponent,
  selectors: [["app-navigator-form"]],
  decls: 2,
  vars: 2,
  consts: [[3, "model", "submit"]],
  template: function AppNavigatorFormComponent_Template(rf, ctx) {
    if (rf & 1) {
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](0);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](1, "as-meta-form", 0);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("submit", function AppNavigatorFormComponent_Template_as_meta_form_submit_1_listener($event) {
        return ctx.onSubmit($event);
      });
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
    }
    if (rf & 2) {
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate1"]("", ctx.header, "\n");
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](1);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("model", ctx.model);
    }
  },
  dependencies: [angular2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsMetaFormComponent],
  styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
  encapsulation: 2
});

/***/ }),

/***/ 3395:
/*!********************************************************************************!*\
  !*** ./src/app/navigator/app-navigator-table/app-navigator-table.component.ts ***!
  \********************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppNavigatorTableComponent": () => (/* binding */ AppNavigatorTableComponent)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var _app_navigator_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../app-navigator.service */ 1341);
/* harmony import */ var angular2_simplicity__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! angular2-simplicity */ 9533);




class AppNavigatorTableComponent {
  constructor(router, route, service) {
    this.router = router;
    this.route = route;
    this.service = service;
    this.loader = (query, callback) => {
      const url = new URL(window.location.origin + "/" + this.link);
      url.searchParams.append("index", query.index + "");
      url.searchParams.append("limit", query.limit + "");
      fetch(url.toString()).then(response => response.json()).then(response => {
        callback(response.rows, response.size, response.$schema);
      });
    };
    route.queryParams.subscribe(params => {
      this.header = atob(params["link"]);
    });
  }
  ngOnInit() {
    let queryParams = this.route.queryParams;
    let queryParam = queryParams.value["link"];
    this.link = atob(queryParam);
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
AppNavigatorTableComponent.ɵfac = function AppNavigatorTableComponent_Factory(t) {
  return new (t || AppNavigatorTableComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_2__.ActivatedRoute), _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_app_navigator_service__WEBPACK_IMPORTED_MODULE_0__.AppNavigatorService));
};
AppNavigatorTableComponent.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
  type: AppNavigatorTableComponent,
  selectors: [["app-navigator-table"]],
  decls: 2,
  vars: 2,
  consts: [[3, "items", "rowClick", "load"]],
  template: function AppNavigatorTableComponent_Template(rf, ctx) {
    if (rf & 1) {
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](0);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](1, "as-meta-table", 0);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("rowClick", function AppNavigatorTableComponent_Template_as_meta_table_rowClick_1_listener($event) {
        return ctx.onRowClick($event);
      })("load", function AppNavigatorTableComponent_Template_as_meta_table_load_1_listener($event) {
        return ctx.onLoad($event);
      });
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
    }
    if (rf & 2) {
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate1"]("", ctx.header, "\n");
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](1);
      _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("items", ctx.loader);
    }
  },
  dependencies: [angular2_simplicity__WEBPACK_IMPORTED_MODULE_3__.AsMetaTableComponent],
  styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"],
  encapsulation: 2
});

/***/ }),

/***/ 3854:
/*!*****************************************************!*\
  !*** ./src/app/navigator/app-navigator.resolver.ts ***!
  \*****************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppNavigatorResolver": () => (/* binding */ AppNavigatorResolver)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);

class AppNavigatorResolver {
  resolve(route, state) {
    let link = atob(route.queryParams["link"]);
    return fetch(link).then(response => response.json());
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

/***/ }),

/***/ 1341:
/*!****************************************************!*\
  !*** ./src/app/navigator/app-navigator.service.ts ***!
  \****************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppNavigatorService": () => (/* binding */ AppNavigatorService)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);

class AppNavigatorService {
  constructor() {}
}
AppNavigatorService.ɵfac = function AppNavigatorService_Factory(t) {
  return new (t || AppNavigatorService)();
};
AppNavigatorService.ɵprov = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjectable"]({
  token: AppNavigatorService,
  factory: AppNavigatorService.ɵfac,
  providedIn: 'root'
});

/***/ }),

/***/ 4431:
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ 4497);
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app/app.module */ 6747);


_angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__.platformBrowser().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_0__.AppModule).catch(err => console.error(err));

/***/ })

},
/******/ __webpack_require__ => { // webpackRuntimeModules
/******/ var __webpack_exec__ = (moduleId) => (__webpack_require__(__webpack_require__.s = moduleId))
/******/ __webpack_require__.O(0, ["vendor"], () => (__webpack_exec__(4431)));
/******/ var __webpack_exports__ = __webpack_require__.O();
/******/ }
]);
//# sourceMappingURL=main.js.map