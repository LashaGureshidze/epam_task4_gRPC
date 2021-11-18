import grpc
import ping_pb2_grpc as pb2_grpc
import ping_pb2 as pb2


class PingClient(object):
    """
    Client for gRPC functionality
    """

    def __init__(self):
        self.host = 'localhost'
        self.server_port = 8080

        # instantiate a channel
        self.channel = grpc.insecure_channel(
            '{}:{}'.format(self.host, self.server_port))

        # bind the client and the server
        self.stub = pb2_grpc.PingServiceStub(self.channel)

    def get_url(self, message):
        """
        Client function to call the rpc for ping
        """
        message = pb2.PingRequest(message=message)
        print(f'{message}')
        return self.stub.ping(message)


if __name__ == '__main__':
    client = PingClient()
    result = client.get_url(message="Hello Server you there?")
    print(f'{result}')