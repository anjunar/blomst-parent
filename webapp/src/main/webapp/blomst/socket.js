const socket = new WebSocket("ws://localhost:8080/app/socket")

const handlersRegistry = new Map();

socket.addEventListener("message", (event) => {
    let data = JSON.parse(event.data);

    let handlers = handlersRegistry.get(data.type)

    if (handlers) {
        for (const handler of handlers) {
            handler(data)
        }
    }
});

export const broadCaster = new class BroadCaster {

    register(name, callback) {
        let handlers = handlersRegistry.get(name);
        if (handlers) {
            handlers.push(callback);
        } else {
            handlers = [callback];
            handlersRegistry.set(name, handlers);
        }
    }

    unregister(name, callback) {
        let handlers = handlersRegistry.get(name);
        let indexOf = handlers.indexOf(callback);
        handlers.splice(indexOf, 1);
    }

    fire(name, object) {
        socket.send(JSON.stringify({
            type : name,
            ...object
        }))
    }

}

