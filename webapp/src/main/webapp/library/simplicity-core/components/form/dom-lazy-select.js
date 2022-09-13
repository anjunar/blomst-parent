import {customComponents} from "../../simplicity.js";
import {libraryLoader} from "../../processors/loader-processor.js";
import DomInput from "../../directives/dom-input.js";
import DomForm from "../../directives/dom-form.js";
import {debounce, Input, isEqual, Membrane, mix} from "../../services/tools.js";

class DomLazySelect extends mix(HTMLElement).with(Input) {

    index = 0;
    limit = 5;
    size;

    window = [];
    items = () => {}
    open = false;
    label = "name";

    placeholder = "";
    defaultValue = "";
    name;
    disabled = "false";
    multiSelect = false;
    showSelected = false;
    search = "";
    id = "id";

    preInitialize() {
        Membrane.track(this, {
            property : "model",
            element : this,
            handler : () => {
                this.render();
            }
        });
    }

    initialize() {
        if (this.multiSelect) {
            this.model = this.model || [];
        } else {
            this.model = this.model || null;
        }

        let listener = () => {
            if (this.open) {
                this.open = false;
            }
        };

        let input = this.querySelector("input");
        input.placeholder = this.placeholder;

        window.addEventListener("click", listener)

        DomLazySelect.prototype.destroy = () => {
            window.removeEventListener("click", listener);
        }

        Membrane.track(this, {
            property : "search",
            element : this,
            handler : debounce(() => {
                this.load();
            }, 300)
        })

        if (this.name) {
            let domForm = this.queryUpwards((element) => {
                return element instanceof DomForm
            });
            if (domForm) {
                domForm.register(this);
            }
        }
    }

    render() {
        let input = this.querySelector("input");

        if (this.model) {
            input.disabled = true;
            if (this.multiSelect) {
                if (this.label instanceof Array) {
                    input.value = this.model.map(model => this.label.map(label => model[label]).join(" ")).join(", ")
                } else {
                    input.value = this.model.map(model => model[this.label]).join(", ")
                }
            } else {
                if (this.label instanceof Array) {
                    input.value = this.label.map((label) => this.model[label]).join(" ")
                } else {
                    input.value = this.model[this.label]
                }
            }
        } else {
            if (input) {
                input.value = ""
            }
        }

        if (input) {
            input.dispatchEvent(new Event("input"));
        }
    }

    inputWidth() {
        let method = () => {
            let input = this.querySelector("input");
            if (input) {
                return input.offsetWidth;
            }
            return 0;
        }
        let resonator = (callback, element) => {
            let listener = () => {
                callback();
            };

            window.addEventListener("resize", listener)
            element.addEventListener("removed", () => {
                window.removeEventListener("resize", listener)
            })
        }
        return {method, resonator}
    }

    onItemClicked(event, item) {
        event.stopPropagation();
        if (this.multiSelect) {
            let find = this.model.find(model => isEqual(model, item));
            if (! find) {
                this.model.push(item);
            }
        } else {
            this.model = item;
        }

        this.render();

        this.open = false;
        this.dispatchEvent(new CustomEvent("model"))
        this.dispatchEvent(new CustomEvent("input"));
        return false;
    }

    openOverlay(event) {
        event.stopPropagation();
        if (this.disabled === undefined || this.disabled === "false") {
            this.load();
        }
        return false;
    }

    up(event) {
        event.stopPropagation();
        this.index -= this.limit
        this.load();
        return false;
    }

    down(event) {
        event.stopPropagation();
        this.index += this.limit;
        this.load();
        return false;
    }

    checkbox(event) {
        event.stopPropagation();
        return false;
    }

    showSelectedClick(event) {
        event.stopPropagation();

        this.showSelected = ! this.showSelected;

        if (this.showSelected) {
            this.index = 0;
            this.size = this.model.length;
            if (this.multiSelect) {
                this.window = this.model.slice(this.index, this.index + this.limit);
            } else {
                this.window = [this.model];
            }
        } else {
            this.load();
        }

        return false;
    }

    searchBox(event) {
        event.stopPropagation();
        return false;
    }

    onWheel(event) {
        event.stopPropagation();
        event.preventDefault();

        if (event.deltaY > 0) {
            if (this.index + this.limit < this.size) {
                this.index += this.limit
                this.load();
            }
        } else {
            if (this.index > 0) {
                this.index -= this.limit
                this.load();
            }
        }

        return false;
    }

    load() {
        if (this.showSelected) {
            this.open = true;
            if (this.multiSelect) {
                this.window = this.model.slice(this.index, this.index + this.limit);
            } else {
                this.window = [this.model];
            }
        } else {
            this.items({index : this.index, limit : this.limit, value : this.search}, (data, size) => {
                this.size = size;
                this.open = true;
                this.showSelected = false;
                this.window = data.map(item => this.proxyFactory(item, this));
                let viewport = this.queryUpwards((element) => element.localName === "viewport");

                let height = 14 + 39 + data.length * 42;
                let selectBoundingClientRect = this.getBoundingClientRect();
                let viewPortBoundingClientRect = viewport.getBoundingClientRect();
                if (selectBoundingClientRect.top + height > viewPortBoundingClientRect.top + viewPortBoundingClientRect.height) {
                    let overlay = this.querySelector("div.overlay");
                    overlay.style.top = "initial"
                    overlay.style.bottom = "24px"
                }
            })
        }
    }

    proxyFactory(item, self) {
        return new Proxy(item, {
            get(target, p, receiver) {
                if (p === "selected") {
                    if (self.multiSelect) {
                        return self.model.find(model => target[self.id] === model[self.id])
                    } else {
                        if (self.model) {
                            return self.model[self.id] === target[self.id];
                        }
                        return false;
                    }
                }
                return Reflect.get(target, p, receiver);
            },
            set(target, p, value, receiver) {
                if (p === "selected") {
                    let oldValue = Reflect.get(receiver, p, receiver);
                    if (oldValue) {
                        if (self.multiSelect) {
                            let find = self.model.find(model => isEqual(model, target));
                            let indexOf = self.model.indexOf(find)
                            self.model.splice(indexOf, 1)
                            self.oringinalModel.splice(indexOf, 1)
                        } else {
                            self.model = null
                        }
                        self.dispatchEvent(new CustomEvent("model"))
                        self.dispatchEvent(new CustomEvent("input"));
                    } else {
                        if (self.multiSelect) {
                            self.model.push(receiver);
                            self.oringinalModel.push(receiver);
                        } else {
                            self.model = receiver;
                        }
                    }
                    self.window.forEach((item) => item.fire())
                }
                return Reflect.set(target, p, value, receiver);
            },
            getOwnPropertyDescriptor(target, p) {
                if (p === "selected") {
                    let object = {
                        get selected() {
                            return false
                        },
                        set selected(value) {
                        }
                    }
                    return Reflect.getOwnPropertyDescriptor(object, "selected");
                }
                return Reflect.getOwnPropertyDescriptor(target, p);
            }
        });
    }

    attributeChangedCallback(name, oldValue, newValue) {
        switch (name) {
            case "model" : {
                if (newValue) {
                    if (newValue instanceof Array) {
                        this.model = newValue.map(model => this.proxyFactory(model, this));
                        this.oringinalModel = newValue;
                    } else {
                        this.model = this.proxyFactory(newValue, this);
                    }
                }
            } break;
            case "placeholder" : {
                this.placeholder = newValue;
            } break;
            case "items" : {
                this.items = newValue;
            } break;
            case "label" : {
                this.label = newValue;
            } break;
            case "name" : {
                this.name = newValue
            } break;
            case "disabled" : {
                this.disabled = newValue;
            } break
            case "multiselect" : {
                this.multiSelect = newValue === "true"
            } break;
            case "id" : {
                this.id = newValue;
            }
        }
    }

    static get components() {
        return [DomInput]
    }

    static get observedAttributes() {
        return [
            {
                name: "model",
                binding: "two-way"
            }, {
                name : "placeholder",
                binding : "input"
            }, {
                name : "items",
                binding : "input"
            }, {
                name : "label",
                binding : "input"
            }, {
                name : "name",
                binding : "input"
            }, {
                name : "disabled",
                binding: "input"
            }, {
                name : "multiselect",
                binding: "input"
            }, {
                name : "id",
                binding: "input"
            }
        ]
    }

    static get template() {
        return libraryLoader("simplicity-core/components/form/dom-lazy-select.html")
    }

}

export default customComponents.define("dom-lazy-select", DomLazySelect)