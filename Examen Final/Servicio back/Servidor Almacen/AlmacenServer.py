import socket

host, port = '127.0.0.1', 8888

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serversocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serversocket.bind((host, port))
serversocket.listen(1)

print('servidor en el puerto', port)

while True:
    connection, address = serversocket.accept()
    request = connection.recv(1024).decode('utf-8')
    print(request)

    string_list = request.split(' ')
    metodh = string_list[0]
    request_file = string_list[1]

    print('Cliente Request', request_file)

    myfile = request_file.split('?')[0]
    myfile = myfile.lstrip('/')

    if(myfile == ''):
        myfile = 'index.html'
    
    try:
        file = open(myfile, 'rb')
        response = file.read()
        file.close()

        header = 'HTTP/1.1 200 OK\n'

        if(myfile.endswith('.jpg')):
            mimetype = 'image/jpg'
        elif(myfile.endswith('.css')):
            mimetype = 'text/css'
        elif(myfile.endswith('.pdf')):
            mimetype = 'application/pdf'
        else:
            mimetype = 'text/html'
        header += 'Content-Type: '+str(mimetype)+'\n\n'
    except Exception as e:
        header = 'HTTP/1.1 404 Not Found\n'
        response = '<html><body>ERROR: 404</body></html>'.encode('utf-8')
    
    final_response = header.encode('utf-8') 
    final_response += response

    connection.send(final_response)
    connection.close()