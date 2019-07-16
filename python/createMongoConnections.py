import threading
from pymongo import MongoClient
import argparse


def worker(num, db):
    print("Thread number %s" % num)
    try:
        db.command('ismaster')
    except Exception as e:
        print("Exception: %s" % e)
    return


def main(args):
    client = MongoClient('mongodb://' + args.mongo + ':27017', maxPoolSize=None)
    database = client.admin
    threads = []
    i = 0
    while i < args.max_con:
        t = threading.Thread(target=worker, args=(i, database))
        threads.append(t)
        # Start thread.
        t.start()
        i = i+1

    for t in threads:
        t.join()


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Process some integers.')
    parser.add_argument('max_con', type=int, help='maximum connections')
    parser.add_argument('mongo', help='Mongo host')

    args = parser.parse_args()
    main(args)
