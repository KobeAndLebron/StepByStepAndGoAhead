#!/bin/bash

## SYNOPSIS: grep [options] [-e|--regexp=]pattern [FILE...]
##           grep [OPTIONS] [-e PATTERN | -f FILE] [FILE...]
## DESCRIPTION: grep  searches  the  named  input  FILEs(space separated)
## (or standard input if no files are named, or if a single hyphen-minus (-) is given as file name(see EG1.))
## for lines containing a match to the given PATTERN.  By default, grep prints the matching lines.

## OPTIONS:
##    -f FILE, --file=FILE
##      Obtain patterns from FILE, one per line.  The empty file contains zero patterns, and therefore matches nothing.
grepFile='greped.txt'

## 用file的内容作为pattern, 去匹配另一个file
grep --color=always -f ${grepFile} ${grepFile}

## equivalent
grep --color=always --regexp="GNU" ${grepFile}
grep --color=always -e "GNU" ${grepFile}

## 仅仅匹配iS(忽略大小写)两次, 并且打印匹配行在文件的行数.(The next commands blow are equivalent.)
## -m NUM, --max-count=NUM
##      Stop  reading  a file after NUM matching lines.  If the input is standard input from a regular file,
##     and NUM matching lines are output, grep ensures that the standard input is positioned to just after
##     the last matching line before exiting, regardless of the presence of trailing context lines.
##      This enables a calling process to resume a search.  When grep stops after NUM matching lines,
##     it outputs any trailing context lines.  When the -c or --count option is also used, grep does not output
##     a count greater than NUM.  When the -v or --invert-match option is also used, grep stops after outputting
##     NUM non-matching lines.
grep --max-count=2 --line-number --ignore-case "iS" ${grepFile}
grep -m 2 -ni "Is" ${grepFile}
# Result(+++ +++ is matched word.):
## 5: Everyone +++is+++ permitted to copy and d+++is+++tribute verbatim copies
## 6: of th+++Is+++ license document, but changing it +++is+++ not allowed.

## equivalent.
## Invert the sense of matching, to select non-matching lines.
grep --invert-match --ignore-case --line-number "LICenSe" ${grepFile}
grep -vin "LICENSE" ${grepFile}

#  With the -o or  --only-matching  option, this has no effect and a warning is given.
grep --before-context=1 --after-context=2 -i "LIcense" ${grepFile}
grep -B1 -A 2 -vi "LIcense" ${grepFile}

## Other options:
## -H, --with-filename
##    Print the file name for each match.  This is the default when there is more than one file to search.
##
## -h, --no-filename
##    Suppress the prefixing of file names on output.  This is the default when there is only one
#    file (or only standard input) to search.
### EG1. Grep multiple files and standard input.
echo "LICENSE" | grep -n "LICENSE" ${grepFile} -
###  result：
###  greped.txt:1:                    GNU GENERAL PUBLIC LICENSE
###  (standard input):1:LICENSE

echo "LICENSE" | grep -h "LICENSE" ${grepFile} -
###  result：
###                    GNU GENERAL PUBLIC LICENSE
###LICENSE


## -L, --files-without-match
##      Suppress normal output; instead print the name of each input file from which no output
##     would normally have been printed.  The scanning will stop on the first match.
##
##  -l, --files-with-matches
##      Suppress normal output; instead print the name of each input file from which output would
##     normally have been printed.  The scanning will stop on the first match.   (-l  is  specified
##     by POSIX.)
echo "LICENSE" | grep -l "LICENSE" ${grepFile} -  # result: greped.txt
                                                  #         (standard input)
echo "LICENSE" | grep -lv "LICENSE" ${grepFile} -  # result: grep.txt


## -c, --count
##     Suppress  normal  output;  instead  print  a  count of matching lines for each input file.
#     With the -v, --invert-match option (see below), count non-matching lines.  (-c is specified by
##    POSIX.)
## 列出不包含LICENSE的行的数量.
grep -vc "LICENSE" greped.txt # result:674

## ------------------------------------------------------------- ##


## Basic vs Extended Regular Expressions
##     In basic regular expressions the meta-characters ?, +, {, |, (, and ) lose their special meaning;
##    instead use the backslashed versions \?, \+, \{, \|, \(, and \).  Not include character [ ] and ^ $ - .

## equivalent.
grep --basic-regexp "\(most\|are\)" ${grepFile}
grep -G "\(most\|are\)" ${grepFile}
grep "\(most\|are\)" ${grepFile} # grep "\(most\|are)" ${grepFile} --- grep: Unmatched ( or \(,
                                 # 因为\(被赋予了特殊含义, 被当做正则的(使用, 所以(未被匹配

grep --extend-regexp "(most|are)" ${grepFile}
grep -E "(most|are)" ${grepFile} # grep -E "\(most|are)" ${grepFile} --- grep: Unmatched ) or \)
                                 # 因为)被赋予了特殊含义, 被当做正则的)使用, 所以)未被匹配
egrep "(most|are)" ${grepFile}

## -------------------------------------------------------------- ##

## Anchoring
##        The caret ^ and the dollar sign $ are meta-characters that respectively match the empty string
##       at the beginning and end of a line.


## -------------------------------------------------------------- ##


## POSIX(character class)
## Syntax:
grep -E "[:upper:]{6}" ${grepFile}
##grep: character class syntax is [[:space:]], not [:space:]

##   [:upper:] 大写字母,等价于A-Z, A-Z不能单独出现(单独出现就表示字面上的A-Z要想表示大写字母的pattern, 必须以[A-Z]的形式出现).
grep "[[:upper:]]{1}" ${grepFile} # {和}失去特殊含义, 按照普通字符{/}处理～ wrong

## equivalent(大写字母连续出现六次)
grep -E "[[:upper:]]{6}" ${grepFile}
grep -E "[A-Z]{6}" ${grepFile}

## equivalent
grep [^[:upper:]] ${grepFile} #小写字母
grep -E [^A-Z] ${grepFile} #小写字母
# [和]在basic regexp和extend regexp含义相同

##   [:space:] 空格
## t或空格一共匹配两次, 即tt t空格 空格t 空格空格 都能匹配到.
grep -E "[t[:space:]]{2}" ${grepFile}