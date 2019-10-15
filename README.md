# Logic
- Each entry from crontab file is read
- Each string representation is converted into CrontabEntry object representation using CrontabEntryParser
- CrontabEntryParser::toCrontabEntry returns Optional i.e. if crontab file entry is not as expected it will return no value.
  As of now only check for valid entry is to divide the crontab file entry has 3 parts. "HH mm executable"
- A CrontabEntry holds the executable file name and all intervals at which the executable is expected to be executed.
- ExecutionTimeCalculator::findFirstExecutions returns the of first time execution times.


# Main start
- Cron is the app file
- Privide complete file path & time in form