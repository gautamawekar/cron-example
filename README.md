# Logic
- Each entry from crontab file is read
- Each string representation is converted into _CrontabEntry_ object representation using _CrontabEntryParser_.
  During parsing expected execution times is calculated.
- _CrontabEntryParser::toCrontabEntry_ returns _Optional_ i.e. if cron entry is not in expected format it will return no value.
  Cron entry has valid 3 parts viz. *HH mm executable*
- A _CrontabEntry_ holds the executable file name and all intervals at which the executable is expected to be executed.
- ExecutionTimeCalculator::findFirstExecutions returns the of first time execution times.


# Main start
- Cron is the app file
- Provide complete file path & time in form