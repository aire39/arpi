import socket

isRunning = True

HOST, PORT = '', 8888

listen_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
listen_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
listen_socket.bind((HOST,PORT))
listen_socket.listen(5)

print ('Serving HTTP on port... %s' % PORT)

while isRunning == True:
	client_connection, client_address = listen_socket.accept()
	request = client_connection.recv(1024)

	action = request.decode()
	if action == "end":
		isRunning = False

	print (request)

	http_response = """OK"""
	client_connection.sendall( http_response.encode('utf-8') )


	client_connection.close()

	#_JAVA_OPTIONS: -Djava.net.preferIPv4Stack=true