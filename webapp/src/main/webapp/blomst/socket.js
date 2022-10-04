let socket;

(function connect() {
    socket = new WebSocket(`wss://${window.location.host}/socket`)

    let interval = setInterval(() => {
        socket.send("heartbeat()")
    }, 1000 * 60);

    let onBeforeUnload = () => {
        socket.close();
        window.clearInterval(interval);
        window.removeEventListener("beforeunload", onBeforeUnload);
        socket.removeEventListener("message", onMessage)
    };

    window.addEventListener("beforeunload", onBeforeUnload)

    socket.onclose = () => {
        console.log("Connection Error... try reconnection in 1s...")
        setTimeout(() => {
            connect();
        }, 1000)
    }

    socket.onerror = () => {
        socket.close();
        window.clearInterval(interval);
        window.removeEventListener("beforeunload", onBeforeUnload)
        socket.removeEventListener("message", onMessage)
    }

    let onMessage = (event) => {
        let regex = /([\w-]+)\((.*)\)/;
        let regexResult = regex.exec(event.data);

        let handlers = handlersRegistry.get(regexResult[1])

        if (handlers) {
            for (const handler of handlers) {
                if (regexResult[2]) {
                    handler(JSON.parse(regexResult[2]))
                } else {
                    handler()
                }
            }
        }
    };

    socket.addEventListener("message", onMessage);


})()


const handlersRegistry = new Map();

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
        if (object) {
            socket.send(`${name}(${JSON.stringify(object)})`)
        } else {
            socket.send(`${name}()`)
        }
    }

}

