from pymongo import MongoClient
from pymongo.errors import ConnectionFailure
import argparse


def main(args):
    i = 0
    while i < args.max_con:
        client = MongoClient('mongodb://' + args.mongo + ':27017')
        try:
            # The ismaster command is cheap and does not require auth.
            client.admin.command('ismaster')

        except ConnectionFailure:
            print("Server not available")
        i = i + 1


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Process some integers.')
    parser.add_argument('max_con', type=int, help='maximum connections')
    parser.add_argument('mongo', help='Mongo host')

    args = parser.parse_args()
    main(args)
